package org.wso2.mb.integration.tests.server.mgt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.automation.api.clients.authenticators.AuthenticatorClient;
import org.wso2.carbon.automation.engine.context.TestUserMode;
import org.wso2.carbon.automation.engine.frameworkutils.FrameworkPathUtil;
import org.wso2.carbon.integration.common.utils.mgt.ServerConfigurationManager;
import org.wso2.mb.integration.common.clients.operations.utils.AndesClientUtils;
import org.wso2.mb.integration.common.utils.backend.MBIntegrationBaseTest;

import java.io.File;

public class RenameVirtualHostTestCase extends MBIntegrationBaseTest {
    private static final Log log = LogFactory.getLog(RenameVirtualHostTestCase.class);
    private ServerConfigurationManager serverManager = null;

    @BeforeClass(alwaysRun = true)
    public void init() throws Exception {
        super.init(TestUserMode.SUPER_TENANT_USER);
        AndesClientUtils.sleepForInterval(15000);
    }

    @Test(groups = "wso2.mb", description = "Change the andes-virtualhosts config to rename the existing virtual host and start MB")
    public void changeVirtualHostConfigAndStartMB() throws Exception {

        //changing the andes-virtualhosts.xml with the new configuration and restarts the server
        serverManager = new ServerConfigurationManager(automationContext);
        serverManager.applyConfiguration(new File(FrameworkPathUtil.getSystemResourceLocation() + File.separator
                + "artifacts" + File.separator + "MB" + File.separator + "configs" + File.separator + "advanced"
                + File.separator + "andes-virtualhosts.xml"),
                new File(ServerConfigurationManager.getCarbonHome() + File.separator + "repository" + File.separator
                +"conf"+ File.separator + "advanced" + File.separator + "andes-virtualhosts.xml") , true, true);

        // Authenticating to the started MB server
        AuthenticatorClient authenticatorClient =
                new AuthenticatorClient(automationContext.getContextUrls().getServiceUrl());
        authenticatorClient.login(automationContext.getSuperTenant().getContextUser().getUserName(),
                automationContext.getSuperTenant().getContextUser().getPassword(), "127.0.0.1");
        log.info("Succesfully logged into MB.");

        // Restoring the andes-virtualhosts config
        serverManager.restoreToLastConfiguration();
    }
}
