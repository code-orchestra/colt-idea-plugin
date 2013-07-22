package codeOrchestra.plugin;

import codeOrchestra.plugin.view.COLTConfigurationPage;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.*;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Dima Kruk
 * @author Alexander Eliseyev
 */
@State(
    name = "COLTSettings",
    storages = {
            @Storage(
                file = StoragePathMacros.APP_CONFIG + "/colt_settings.xml")
    }
)
public class COLTSettings implements PersistentStateComponent<COLTSettings.State>, SearchableConfigurable, ApplicationComponent {

    private static COLTSettings instance = null;

    public static COLTSettings getInstance() {
        if (instance == null) {
            instance = ApplicationManager.getApplication().getComponent(COLTSettings.class);
        }
        return instance;
    }
    private State myState = new State();

    private COLTConfigurationPage configurationPage;

    @Nullable
    @Override
    public State getState() {
        return myState;
    }

    @Override
    public void loadState(State state) {
        myState = state;
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(getSecurityToken());
    }

    public void invalidate() {
        setSecurityToken("");
    }

    @NotNull
    @Override
    public String getId() {
        return "COLTSettings";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String s) {
        return null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "COLT";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public synchronized JComponent createComponent() {
        if (configurationPage == null) {
            configurationPage = new COLTConfigurationPage(this);
        }
        return configurationPage.getContentPane();
    }

    @Override
    public boolean isModified() {
        return configurationPage.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        configurationPage.apply();
    }

    @Override
    public void reset() {
        configurationPage.reset();
    }

    @Override
    public synchronized void disposeUIResources() {
        if (configurationPage != null) {
            configurationPage.dispose();
        }
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "COLT Settings";
    }

    public String getCOLTPath() {
        return myState.coltPath;
    }

    public void setCOLTPath(String path) {
        myState.coltPath = path;
    }

    public static class State {
        public String securityToken = "";
        public String coltPath = "";
    }

    public String getSecurityToken() {
        return myState.securityToken;
    }

    public void setSecurityToken(String token) {
        myState.securityToken = token;
    }
}
