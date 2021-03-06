/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package com.ca.mfaas.apicatalog.controllers.api;

import com.ca.mfaas.apicatalog.controllers.handlers.CatalogApiDocControllerExceptionHandler;
import com.ca.mfaas.apicatalog.services.status.APIServiceStatusService;
import com.ca.mfaas.apicatalog.services.status.model.ApiDocNotFoundException;
import com.ca.mfaas.error.ErrorService;
import com.ca.mfaas.error.impl.ErrorServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {CatalogApiDocController.class}, secure = false)
@DirtiesContext
public class CatalogApiDocControllerApiDocNotFoundTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getApiDocAnfFailThenThrowApiDocNotFoundException() throws Exception {
        this.mockMvc.perform(get("/apidoc/service2/v1"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.messages[?(@.messageNumber == 'APIC0005')].messageContent",
                hasItem("API Documentation not retrieved, Really bad stuff happened")));
    }

    @Configuration
    static class ContextConfiguration {

        @MockBean
        private APIServiceStatusService apiServiceStatusService;

        @Bean
        public CatalogApiDocController catalogApiDocController() {
            when(apiServiceStatusService.getServiceCachedApiDocInfo("service2", "v1"))
                .thenThrow(new ApiDocNotFoundException("Really bad stuff happened"));

            verify(apiServiceStatusService, never()).getServiceCachedApiDocInfo("service2", "v1");

            return new CatalogApiDocController(apiServiceStatusService);
        }

        @Bean
        public ErrorService errorService() {
            return new ErrorServiceImpl("/messages.yml");
        }

        @Bean
        public CatalogApiDocControllerExceptionHandler catalogApiDocControllerExceptionHandler() {
            return new CatalogApiDocControllerExceptionHandler(errorService());
        }
    }
}
