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
