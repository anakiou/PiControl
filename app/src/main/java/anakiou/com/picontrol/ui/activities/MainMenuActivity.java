package anakiou.com.picontrol.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import anakiou.com.picontrol.ui.fragments.MainMenuFragment;

public class MainMenuActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new MainMenuFragment();
    }

}
