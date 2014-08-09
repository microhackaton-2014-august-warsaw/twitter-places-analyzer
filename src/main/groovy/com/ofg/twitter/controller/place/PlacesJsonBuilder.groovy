package com.ofg.twitter.controller.place

import groovy.text.SimpleTemplateEngine
import groovy.transform.TypeChecked

@TypeChecked
class PlacesJsonBuilder {

    String buildPlacesJson(long pairId, Map<String, Optional<Place>> places) {
        return """{
                        "pairId" : $pairId,
                        "origin" : "twitter",
                        "places" : [${places.collect { String tweetId, Optional<Place> place ->
                            
                            return new SimpleTemplateEngine().createTemplate(JSON_PLACES_RESPONSE_TEMPLATE)
                            .make([pairId: pairId,
                                   tweetId : tweetId,
                                   place: place])
                            .toString()
                        }.join(',')}]
                   }"""
    }


    private static final String JSON_PLACES_RESPONSE_TEMPLATE = '''
                {
                    "tweetId" : "$tweetId"
                    <% if (place.present) { %> 
                        ,"place" :
                        {
                            "name":"${place.get().placeDetails.name}",
                            "countryCode": "${place.get().placeDetails.countryCode}"
                        },
                        "probability" : "${place.get().placeResolutionProbability}",
                        "origin" : "${place.get().placeResolutionOrigin}"
                    <% } %>
                }
                '''

}
