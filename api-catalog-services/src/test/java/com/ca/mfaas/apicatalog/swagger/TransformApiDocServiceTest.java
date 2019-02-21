/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */

package com.ca.mfaas.apicatalog.swagger;


import com.ca.mfaas.apicatalog.services.cached.model.ApiDocInfo;
import com.ca.mfaas.product.model.ApiInfo;
import com.ca.mfaas.product.routing.RoutedService;
import com.ca.mfaas.product.routing.RoutedServices;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RunWith(MockitoJUnitRunner.Silent.class)
public class TransformApiDocServiceTest {
    private TransformApiDocService transformApiDocService;


    @Before
    public void setUp() {
        transformApiDocService = new TransformApiDocService();
    }

    @Test
    public void shouldTransformApiDoc() {
        String serviceId = "Service";
        String swagger = "{\"swagger\":\"2.0\",\"info\":{\"description\":\"REST API for the API Catalog service which is a component of the API Mediation Layer. Use this API to retrieve information regarding catalog dashboard tiles, tile contents and its status, API documentation and status for the registered services.\",\"version\":\"1.0.0\",\"title\":\"API Catalog\"},\"basePath\":\"/apicatalog\",\"tags\":[{\"name\":\"API Catalog\",\"description\":\"Current state information\"},{\"name\":\"API Documentation\",\"description\":\"Service documentation\"}],\"paths\":{\"/apidoc/{service-id}/{api-version}\":{\"get\":{\"tags\":[\"API Documentation\"],\"summary\":\"Retrieves the API documentation for a specific service version\",\"description\":\"Returns the API documentation for a specific service {service-id} and version {api-version}\",\"operationId\":\"getApiDocInfoUsingGET\",\"produces\":[\"application/json;charset=UTF-8\"],\"parameters\":[{\"name\":\"service-id\",\"in\":\"path\",\"description\":\"The unique identifier of the registered service\",\"required\":true,\"type\":\"string\"},{\"name\":\"api-version\",\"in\":\"path\",\"description\":\"The major version of the API documentation (v1, v2, etc.)\",\"required\":true,\"type\":\"string\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"type\":\"string\"},\"responseSchema\":{\"type\":\"string\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"URI not found\"},\"500\":{\"description\":\"An unexpected condition occurred\"}},\"deprecated\":false}},\"/containers\":{\"get\":{\"tags\":[\"API Catalog\"],\"summary\":\"Lists catalog dashboard tiles\",\"description\":\"Returns a list of tiles including status and tile description\",\"operationId\":\"getAllAPIContainersUsingGET\",\"produces\":[\"application/json;charset=UTF-8\"],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/APIContainer\",\"originalRef\":\"APIContainer\"}},\"responseSchema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/APIContainer\",\"originalRef\":\"APIContainer\"}}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}},\"/containers/{id}\":{\"get\":{\"tags\":[\"API Catalog\"],\"summary\":\"Retrieves a specific dashboard tile information\",\"description\":\"Returns information for a specific tile {id} including status and tile description\",\"operationId\":\"getAPIContainerByIdUsingGET\",\"produces\":[\"application/json;charset=UTF-8\"],\"parameters\":[{\"name\":\"id\",\"in\":\"path\",\"description\":\"id\",\"required\":true,\"type\":\"string\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/APIContainer\",\"originalRef\":\"APIContainer\"}},\"responseSchema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/APIContainer\",\"originalRef\":\"APIContainer\"}}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}}},\"definitions\":{\"APIContainer\":{\"type\":\"object\",\"properties\":{\"activeServices\":{\"type\":\"integer\",\"format\":\"int32\"},\"createdTimestamp\":{\"$ref\":\"#/definitions/Calendar\",\"originalRef\":\"Calendar\"},\"description\":{\"type\":\"string\",\"description\":\"The description of the API\",\"allowEmptyValue\":false},\"id\":{\"type\":\"string\",\"description\":\"The API Container Id\",\"allowEmptyValue\":false},\"lastUpdatedTimestamp\":{\"$ref\":\"#/definitions/Calendar\",\"originalRef\":\"Calendar\"},\"services\":{\"type\":\"array\",\"description\":\"A collection of services which are registered with this API\",\"allowEmptyValue\":false,\"items\":{\"$ref\":\"#/definitions/APIService\",\"originalRef\":\"APIService\"}},\"status\":{\"type\":\"string\",\"description\":\"The Status of the container\",\"allowEmptyValue\":false},\"title\":{\"type\":\"string\",\"description\":\"The API Container title\",\"allowEmptyValue\":false},\"totalServices\":{\"type\":\"integer\",\"format\":\"int32\"},\"version\":{\"type\":\"string\",\"description\":\"The version of the API container\",\"allowEmptyValue\":false}},\"title\":\"APIContainer\"},\"APIService\":{\"type\":\"object\",\"properties\":{\"apiDoc\":{\"type\":\"string\",\"description\":\"The API documentation for this service\",\"allowEmptyValue\":false},\"description\":{\"type\":\"string\",\"description\":\"The description of the API service\",\"allowEmptyValue\":false},\"homePageUrl\":{\"type\":\"string\",\"description\":\"The service home page of the API service\",\"allowEmptyValue\":false},\"secured\":{\"type\":\"boolean\",\"description\":\"The security status of the API service\",\"allowEmptyValue\":false},\"serviceId\":{\"type\":\"string\",\"description\":\"The service id\",\"allowEmptyValue\":false},\"status\":{\"type\":\"string\",\"description\":\"The status of the API service\",\"allowEmptyValue\":false},\"title\":{\"type\":\"string\",\"description\":\"The API service name\",\"allowEmptyValue\":false}},\"title\":\"APIService\"},\"Calendar\":{\"type\":\"object\",\"properties\":{\"calendarType\":{\"type\":\"string\"},\"firstDayOfWeek\":{\"type\":\"integer\",\"format\":\"int32\"},\"lenient\":{\"type\":\"boolean\"},\"minimalDaysInFirstWeek\":{\"type\":\"integer\",\"format\":\"int32\"},\"time\":{\"type\":\"string\",\"format\":\"date-time\"},\"timeInMillis\":{\"type\":\"integer\",\"format\":\"int64\"},\"timeZone\":{\"$ref\":\"#/definitions/TimeZone\",\"originalRef\":\"TimeZone\"},\"weekDateSupported\":{\"type\":\"boolean\"},\"weekYear\":{\"type\":\"integer\",\"format\":\"int32\"},\"weeksInWeekYear\":{\"type\":\"integer\",\"format\":\"int32\"}},\"title\":\"Calendar\"},\"Mono«ResponseEntity«string»»\":{\"type\":\"object\",\"title\":\"Mono«ResponseEntity«string»»\"},\"TimeZone\":{\"type\":\"object\",\"properties\":{\"displayName\":{\"type\":\"string\"},\"dstsavings\":{\"type\":\"integer\",\"format\":\"int32\"},\"id\":{\"type\":\"string\"},\"rawOffset\":{\"type\":\"integer\",\"format\":\"int32\"}},\"title\":\"TimeZone\"}}}";
        ResponseEntity<String> response = new ResponseEntity<>(swagger, HttpStatus.OK);
        final RoutedService routedService = new RoutedService("api_v1", "api/v1", "/apicatalog");
        final RoutedService routedService2 = new RoutedService("ui_v1", "ui/v1", "/apicatalog");

        final RoutedServices routedServices = new RoutedServices();
        routedServices.addRoutedService(routedService);
        routedServices.addRoutedService(routedService2);
        ApiInfo apiInfo = new ApiInfo("org.zowe.apicatalog", "api/v1", null, "https://localhost:10014/apicatalog/api-doc", null);
        ApiDocInfo apiDocInfo = new ApiDocInfo(apiInfo, response, routedServices, "https", "localhost:10010");
        String transformApiDoc = transformApiDocService.transformApiDoc(serviceId, apiDocInfo);
        Assert.assertTrue(transformApiDoc.contains("schemes\":[\"https\"]"));
        Assert.assertTrue(transformApiDoc.contains("https://localhost:10010/api/v1/apicatalog/apidoc/Service/v1"));
        Assert.assertTrue(transformApiDoc.contains("host\":\"localhost:10010"));
        Assert.assertTrue(transformApiDoc.contains("basePath\":\"/api/v1/Service"));
    }
}