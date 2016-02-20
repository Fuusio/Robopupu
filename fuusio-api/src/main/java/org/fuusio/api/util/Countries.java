/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://fuusio.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fuusio.api.util;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Countries {

    private static final List<Country> sCountries = new ArrayList<>();

    private static void createCountries() {
        final Locale[] locales = Locale.getAvailableLocales();

        for (final Locale locale : locales) {
            final String iso = locale.getISO3Country();
            final String code = locale.getCountry();
            final String name = locale.getDisplayCountry();

            if (!"".equals(iso) && !"".equals(code) && !"".equals(name)) {
                sCountries.add(new Country(iso, code, name));
            }
        }

        Collections.sort(sCountries, new CountryComparator());
    }

    public static List<Country> getCountries() {
        if (sCountries.isEmpty()) {
            createCountries();
        }
        return sCountries;
    }

    public static String[] getCountryNames() {
        final List<Country> countries = getCountries();
        final String[] names = new String[countries.size()];

        int index = 0;

        for (final Country country : countries) {
            names[index++] = country.getName();
        }

        return names;
    }

    static class CountryComparator implements Comparator<Country> {

        private final Collator mComparator;

        CountryComparator() {
            mComparator = Collator.getInstance();
        }

        @Override
        public int compare(final Country country1, final Country country2) {
            return mComparator.compare(country1.getName(), country2.getName());
        }
    }
}
