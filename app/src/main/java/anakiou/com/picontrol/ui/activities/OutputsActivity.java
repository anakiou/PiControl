package anakiou.com.picontrol.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import anakiou.com.picontrol.ui.fragments.OutputsFragment;

public class OutputsActivity  extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, OutputsActivity.class);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new OutputsFragment();
    }
}
