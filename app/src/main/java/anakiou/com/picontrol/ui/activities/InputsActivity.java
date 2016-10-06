/*
 * Copyright 2016 . Anargyros Kiourkos.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package anakiou.com.picontrol.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import anakiou.com.picontrol.ui.fragments.InputsFragment;

public class InputsActivity  extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, InputsActivity.class);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new InputsFragment();
    }

}
