package anakiou.com.picontrol.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import anakiou.com.picontrol.ui.fragments.EditNamesFragment;
import anakiou.com.picontrol.ui.fragments.MainMenuFragment;

public class EditNamesActivity  extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EditNamesActivity.class);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new EditNamesFragment();
    }
}
