//	Copyright 2023 Obzen
//
//	Licensed under the Apache License, Version 2.0 (the "License");
//	you may not use this file except in compliance with the License.
//	You may obtain a copy of the License at
//
//	    http://www.apache.org/licenses/LICENSE-2.0
//
//	Unless required by applicable law or agreed to in writing, software
//	distributed under the License is distributed on an "AS IS" BASIS,
//	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//	See the License for the specific language governing permissions and
//	limitations under the License.

package com.obzen.tagworks.constants;

/**
 * The enum Standard event params.
 */
public enum StandardEventParams {

    /**
     * Visitor id standard event params.
     */
    VISITOR_ID("ozvid"),
    /**
     * Tag event type standard event params.
     */
    TAG_EVENT_TYPE("obz_trg_type"),
    /**
     * Client date standard event params.
     */
    CLIENT_DATE("obz_client_date"),
    /**
     * Page title standard event params.
     */
    PAGE_TITLE("epgtl_nm"),
    /**
     * Dimension standard event params.
     */
    DIMENSION("cstm_d"),
    /**
     * Custom user path standard event params.
     */
    CUSTOM_USER_PATH("obz_user_path"),
    /**
     * Device type standard event params.
     */
    DEVICE_TYPE("obz_dvc_type");

    private final String value;
    StandardEventParams(String value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
