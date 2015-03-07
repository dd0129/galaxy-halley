package com.dianping.data.warehouse.core.jgit;


import com.dianping.data.warehouse.core.common.Const;
import com.dianping.data.warehouse.core.lion.LionUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by hongdi.tang on 14-6-19.
 */
public class JGitUtils {
    private static final String PROJECT_NAME = "galaxy-halley";

    private static Logger logger = LoggerFactory.getLogger(JGitUtils.class);

    public static String getGitPath(String projectName) {
        return LionUtil.getProperty(PROJECT_NAME + "." + projectName.toString() + ".gitPath");
    }

    public static String getWorkerBaseDir(String projectName) {
        return LionUtil.getProperty(PROJECT_NAME + "." + projectName.toString() + ".baseDir");
    }

    public static synchronized boolean pull(String remoteGitFile, String projectName) {
        try {
            String gitDir;
            try {
                gitDir = LionUtil.getProperty(PROJECT_NAME + "." + "MASTER_GIT_DIR");
            } catch (Exception e) {
                logger.error("get config from lion failure", e);
                gitDir = Const.MASTER_GIT_DIR;
            }

//            SshSessionFactory.setInstance(new JschConfigSessionFactory() {
//                public void configure(OpenSshConfig.Host hc, Session session) {
//                    session.setConfig("StrictHostKeyChecking", "no");
//                }
//            });

            String localGitFile = gitDir + File.separator + projectName + File.separator + ".git";
            Repository repository = new FileRepository(localGitFile);
            File gitFile = new File(localGitFile);
            if (!gitFile.exists()) {
                Git.cloneRepository().setURI(remoteGitFile)
                        .setDirectory(new File(gitDir + File.separator + projectName)).call();
            } else {
                logger.info("git pull starts------------------------");
                Git git = new Git(repository);
                PullResult pullResult = git.pull().setTimeout(60).call();
                logger.info("git pull ends------------------------");
                logger.info(pullResult.toString());
            }
            return true;
        } catch (Exception e) {
            logger.error("git pull failure", e);
            return false;
        }
    }
}
