package org.tesis.modulodiagnostico.dtos.response;

public class PlacementContinueResponse {
    private OARecomendationResponse oarecommendationResponse;
    private ProgressTopicResponse progressTopicResponse;

    public OARecomendationResponse getOarecommendationResponse() {
        return oarecommendationResponse;
    }

    public void setOarecommendationResponse(OARecomendationResponse oarecommendationResponse) {
        this.oarecommendationResponse = oarecommendationResponse;
    }

    public ProgressTopicResponse getProgressTopicResponse() {
        return progressTopicResponse;
    }

    public void setProgressTopicResponse(ProgressTopicResponse progressTopicResponse) {
        this.progressTopicResponse = progressTopicResponse;
    }
}
