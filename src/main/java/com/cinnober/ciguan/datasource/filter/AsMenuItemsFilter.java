/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Cinnober Financial Technology AB (cinnober.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.cinnober.ciguan.datasource.filter;

import com.cinnober.ciguan.client.MvcModelAttributesIf;
import com.cinnober.ciguan.data.AsMenuItem;
import com.cinnober.ciguan.impl.As;

/**
 * Filter for menu items
 */
public class AsMenuItemsFilter extends AsUserFilter<AsMenuItem> implements MvcModelAttributesIf {

    public AsMenuItemsFilter(String pMemberId, String pUserId) {
        super(null, pMemberId, pUserId);
    }

    @Override
    public boolean include(AsMenuItem pMenuItem) {
        String tAccService = pMenuItem.getAccService();
        if ("separator".equals(pMenuItem.getId())) {
            // Menu separators are always accessible
            return true;
        }
        if (getUserId() == null) { 
            // not logged in, allow if accService explicitly set to nothing 
            return "".equals(tAccService);
        }
        if (tAccService == null || "".equals(tAccService)) {
            // if logged in and no accService specified, then ok
            return true;
        }
        return As.getAuthorizationHandler().checkAccess(getUserId(), tAccService);
    }

    /**
     * Override to hide the filter
     */
    @Override
    public String toString() {
        return "";
    }
    
}
