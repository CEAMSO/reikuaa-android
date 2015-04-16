package py.gov.contrataciones.reikuaa.view;

import android.content.Context;

import py.gov.contrataciones.reikuaa.model.Graph;

/**
 * Created by alejandro on 03/04/15.
 */
public interface ConfiguracionView {
    public void showProgressBar();
    public void hideProgressBar();
    public void showError();
    public void hideError();
    public void showError404();
    public void hideError404();
    public void showContent();
    public void hideContent();
    public Context getContext();
}
