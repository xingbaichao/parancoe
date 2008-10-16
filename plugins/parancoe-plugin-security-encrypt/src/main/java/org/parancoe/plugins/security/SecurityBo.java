// Copyright 2006-2007 The Parancoe Team
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.parancoe.plugins.security;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *  Useful logic for security.
 * 
 * @author Lucio Benfante
 */
@Component
public class SecurityBo {

    private static final Logger log = Logger.getLogger(SecurityBo.class);
    private Md5PasswordEncoder encoder;

    public SecurityBo() {
            encoder = new Md5PasswordEncoder();
    }

    public String getEncodedPassword(String rawPass, String username) {
        return encoder.encodePassword(rawPass, username);
    }
}
