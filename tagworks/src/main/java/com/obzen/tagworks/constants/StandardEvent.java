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
 * The enum Standard event.
 */
public enum StandardEvent {

    /**
     * Page view standard event.
     */
    PAGE_VIEW("PageView"),
    /**
     * Click standard event.
     */
    CLICK("Click"),
    /**
     * Scroll standard event.
     */
    SCROLL("Scroll"),
    /**
     * Download standard event.
     */
    DOWNLOAD("Download"),
    /**
     * Out link standard event.
     */
    OUT_LINK("OutLink"),
    /**
     * Search standard event.
     */
    SEARCH("Search");

    private final String value;
    StandardEvent(String value) {
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
