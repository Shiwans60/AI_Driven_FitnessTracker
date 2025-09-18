package com.server.aiservice.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.aiservice.Model.Activity;
import com.server.aiservice.Model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {
    private final GeminiService geminiService;
    public Recommendation generateRecommendations(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.GetAnswer(prompt);
        log.info("Response from AI : {}", aiResponse);
        return processairesponse(activity, aiResponse);

    }

    private Recommendation processairesponse(Activity activity, String aiResponse) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");
            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n```", "")
                    .trim();
            log.info("Parsed content from AI : {}", jsonContent);
            JsonNode analysisJson = objectMapper.readTree(jsonContent);
            JsonNode analysisNode = analysisJson.path("analysis");

            StringBuilder fullanalysis = new StringBuilder();
            addanalysissection(fullanalysis, analysisNode, "overall", "Overall: ");
            addanalysissection(fullanalysis, analysisNode, "pace", "Pace: ");
            addanalysissection(fullanalysis, analysisNode, "heartrate", "HeartRate");
            addanalysissection(fullanalysis, analysisNode, "caloriesburnt", "CaloriesBurnt: ");

            List<String> improvements = extractimprovements(analysisJson.path("improvements"));
            List<String> suggestions = extractsuggestions(analysisJson.path("suggestions"));
            List<String> safety = extractsafety(analysisJson.path("safety"));
            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .activity(activity.getType())
                    .recommendation(fullanalysis.toString().trim())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }



    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .activity(activity.getType())
                .recommendation("unable to generate detailed analysis, AI Server is down.")
                .improvements(Collections.singletonList("continue with your current ones"))
                .suggestions(Collections.singletonList("consider consultation"))
                .safety(Arrays.asList("Listen your body","Stay hydrated"))
                .createdAt(LocalDateTime.now())
                .build();

    }

    private List<String> extractsafety(JsonNode safetyNode) {
        List<String> safety = new ArrayList<>();
        if(safetyNode.isArray()){
            safetyNode.forEach(item ->safety.add(item.asText()));

        }
        return safety.isEmpty() ?
                Collections.singletonList("No safety points provided"):
                safety;
    }

    private List<String> extractsuggestions(JsonNode suggestionNode) {
        List<String> suggestions = new ArrayList<>();
        if(suggestionNode.isArray()){
            suggestionNode.forEach( suggestion -> {
                String workout = suggestion.path("workout").asText();
                String Description = suggestion.path("description").asText();
                suggestions.add(String.format("%s: %s", Description, workout));
            });

        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No specific suggestions provide"):
                suggestions;
    }

    private List<String> extractimprovements(JsonNode improvements) {
        List<String> improvementList = new ArrayList<>();
        if(improvements.isArray()){
            improvements.forEach(improvement ->{
                String area = improvement.path("area").asText();
                String detail = improvement.path("RECOMMENDATION").asText();
                improvementList.add(String.format("%s: %s",area,detail));
            });

        }
        return improvementList.isEmpty() ?
                Collections.singletonList("No specific improvement provided"):
                improvementList;
    }

    private void addanalysissection(StringBuilder fullanalysis, JsonNode analysisNode, String key, String prefix) {
        if(!analysisNode.path(key).isMissingNode()){
            fullanalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");

        }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
        Analyse this fitness activity and provide detailed recommendations in the exact json format:
        {
            "analysis": {
                "overall": "overall analysis here",
                "pace": "pace analysis here",
                "heartrate": "heart rate analysis here",
                "caloriesburnt": "Calories analysis here"
            },
        
            "improvements": [
                {
                    "area": "Area name",
                    "RECOMMENDATION": "Detailed recommendations"
                }
            ],
            "suggestions": [
                {
                    "workout": "Workout name",
                    "Description": "Detailed workout Description"
                }
            ],
            "safety": [
                "Safety point 1",
                "Safety point 2",
            ]
        }
        Analyse this activity:
        Activity Type : %s
        Duration: %d minutes
        Calories Burnt: %d
        Additional Metrics: %s
        
        Provide the detailed analysis focusing on performance,improvements, next workout suggestions and safety guidelines
        Ensure the response follows the EXACT JSON format shown above
        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesburnt(),
                activity.getAdiitionalMetrics()
        );


    }

}
