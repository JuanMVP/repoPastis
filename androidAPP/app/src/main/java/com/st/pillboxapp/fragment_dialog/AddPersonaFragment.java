package com.st.pillboxapp.fragment_dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.pillboxapp.R;
import com.st.pillboxapp.viewModel.AddPersonaViewModel;

public class AddPersonaFragment extends Fragment {

    private AddPersonaViewModel mViewModel;

    public static AddPersonaFragment newInstance() {
        return new AddPersonaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_persona_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddPersonaViewModel.class);
        // TODO: Use the ViewModel
    }

}
