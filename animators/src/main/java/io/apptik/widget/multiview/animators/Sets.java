/*
 * Copyright (C) 2015 AppTik Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apptik.widget.multiview.animators;


public class Sets {
    private Sets() {
        throw new IllegalStateException("no instances");
    }

    public static AnimatorSetProvider defaultItemAnimatorSet() {
        return new AnimatorSetProvider() {
            @Override
            public AnimatorProvider getAddAnimProvider() {
                return Providers.defaultAddAnimProvider();
            }

            @Override
            public AnimatorProvider getRemoveAnimProvider() {
                return Providers.defaultRemoveAnimProvider();
            }

            @Override
            public AnimatorProvider getMoveAnimProvider() {
                return Providers.defaultMoveAnimProvider();
            }

            @Override
            public AnimatorProvider getChangeOldItemAnimProvider() {
                return Providers.defaultChangeOldViewAnimProvider();
            }

            @Override
            public AnimatorProvider getChangeNewItemAnimProvider() {
                return Providers.defaultChangeNewViewAnimProvider();
            }
        };
    }

    public static AnimatorSetProvider garageDoorSet() {
        return new AnimatorSetProvider() {
            @Override
            public AnimatorProvider getAddAnimProvider() {
                return Providers.garageDoorAddProvider();
            }

            @Override
            public AnimatorProvider getRemoveAnimProvider() {
                return Providers.garageDoorRemoveProvider();
            }

            @Override
            public AnimatorProvider getMoveAnimProvider() {
                return null;
            }

            @Override
            public AnimatorProvider getChangeOldItemAnimProvider() {
                return Providers.defaultChangeOldViewAnimProvider();
            }

            @Override
            public AnimatorProvider getChangeNewItemAnimProvider() {
                return Providers.defaultChangeNewViewAnimProvider();

            }
        };
    }
}
