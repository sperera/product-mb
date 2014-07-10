package org.wso2.mb.integration.tests.server.mgt;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.wso2.carbon.automation.core.ProductConstant;
import org.wso2.carbon.automation.core.utils.environmentutils.EnvironmentBuilder;
import org.wso2.carbon.integration.common.admin.client.LogViewerClient;
import org.wso2.carbon.integration.common.utils.mgt.ServerConfigurationManager;
import org.wso2.mb.integration.common.utils.backend.MBIntegrationBaseTest;

import java.io.File;

public class RenameVirtualHostTestCase extends MBIntegrationBaseTest {

    private EnvironmentBuilder builder = null;
    private ServerConfigurationManager serverManager = null;
    private LogViewerClient logViewerClient =null;

    @BeforeTest(alwaysRun = true)
    public void startJMSBroker() throws Exception {

        builder = new EnvironmentBuilder().mb(ProductConstant.SUPER_ADMIN_USER_ID);
        serverManager = new ServerConfigurationManager(automationContext);

        if (builder.getFrameworkSettings().getEnvironmentSettings().is_builderEnabled()) {

            //changing the andes-virtualhosts.xml with the new configuration and restarts the server
            serverManager.applyConfiguration(new File(ProductConstant.getResourceLocations(ProductConstant.MB_SERVER_NAME)
                    + File.separator + "configs" + File.separator + "advanced" + "andes-virtualhosts.xml"));

        }

        /*
        FrameworkProperties properties = FrameworkFactory.getFrameworkProperties(ProductConstant.MB_SERVER_NAME);
        UserInfo userInfo = UserListCsvReader.getUserInfo( ProductConstant.SUPER_ADMIN_USER_ID);
        logViewerClient = new LogViewerClient(properties.getProductVariables().getBackendUrl(),
                userInfo.getUserName(), userInfo.getPassword());
                */
    }

    @AfterTest(alwaysRun = true)
    public void stopJMSBroker() throws Exception {
        if (builder.getFrameworkSettings().getEnvironmentSettings().is_builderEnabled()) {
            if (serverManager != null) {
                serverManager.restoreToLastConfiguration();
            }
        }
    }
}
