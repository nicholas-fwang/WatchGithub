//package io.fisache.watchgithub.mock.inject;
//
//import io.fisache.watchgithub.base.AppComponent;
//import io.fisache.watchgithub.base.AppModule;
//import io.fisache.watchgithub.base.BaseApplication;
//import io.fisache.watchgithub.base.DaggerAppComponent;
//import io.fisache.watchgithub.service.github.GithubApiModule;
//import io.fisache.watchgithub.data.component.GroupComponent;
//import io.fisache.watchgithub.data.module.GroupModule;
//
//public class ApplicationMock extends BaseApplication {
//    private AppComponent appComponent;
//    private GithubApiModule githubApiModuleMock;
//    private GroupComponent groupComponent;
//    private GroupModule groupModuleMock;
//
//    public void setGithubApiModuleMock(GithubApiModule githubApiModuleMock) {
//        this.githubApiModuleMock = githubApiModuleMock;
//        setupMockAppComponent();
//    }
//
//    public void setupMockAppComponent() {
//        appComponent = DaggerAppComponent.builder()
//                .appModule(new AppModule(this))
//                .githubApiModule(githubApiModuleMock)
//                .build();
//    }
//
//    public void setGroupModuleMock(GroupModule groupModuleMock) {
//        this.groupModuleMock = groupModuleMock;
//        createGroupComponent();
//    }
//
//    @Override
//    public AppComponent getAppComponent() {
//        return appComponent == null ? super.getAppComponent() : appComponent;
//    }
//
//    @Override
//    public GroupComponent createGroupComponent() {
//        groupComponent = appComponent.plus(groupModuleMock);
//        return groupComponent;
//    }
//
//    @Override
//    public GroupComponent getGroupComponent() {
//        return groupComponent == null ? super.getGroupComponent() : groupComponent;
//    }
//}
