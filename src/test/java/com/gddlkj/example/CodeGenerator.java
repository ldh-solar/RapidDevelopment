package com.gddlkj.example;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeGenerator {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.username}")
    private String username;


    public String getTables() {
        //表名(用逗号分隔: 例如t_user,t_permission)
        String tables;
        JDialog jDialog = new JDialog();
        jDialog.setAlwaysOnTop(true);
        tables = JOptionPane.showInputDialog(jDialog, "表名(用逗号分隔: 例如t_user,t_permission)");
        if (StringUtils.isEmpty(tables)) {
            System.exit(0);
            return null;
        } else
            return tables;
    }


    @Test
    public void main() {
        System.setProperty("java.awt.headless", "false");
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //String projectPath = "E://output";
        String srcPath = projectPath + "/src/main/java";
        gc.setOutputDir(srcPath);
        //设置覆盖
        gc.setFileOverride(true);
        gc.setOpen(false);
        gc.setAuthor("blank");
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(null);
        pc.setParent("com.gddlkj.example");
        pc.setEntity("model.domain");
        pc.setController("web.rest.admin");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };


        // mapper模版
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        // vue模版
        String vuePath = "/templates/index.vue.vm";
        //输出vue模版
        focList.add(new FileOutConfig(vuePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/vue/"
                        + "/" + tableInfo.getEntityPath() +"/list/"+ "index" + ".vue";
            }
        });

        // vue组件模版
        String vueCompomentPath = "/templates/compoment.vue.vm";
        //输出vue模版
        focList.add(new FileOutConfig(vueCompomentPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/vue/"
                        + "/" + tableInfo.getEntityPath() +"/list/compoment/"+ "EditdDialog" + ".vue";
            }
        });

        // service.js模版
        String serviceJs = "/templates/service.js.vm";
        //输出vue模版
        focList.add(new FileOutConfig(serviceJs) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/vue/services"
                        + "/" + tableInfo.getEntityPath() + ".js";
            }
        });


        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        //不输出XML
        templateConfig.setXml(null);

        //自定义controller输出
        templateConfig.setController("/templates/customController.java.vm");

        //自定义service输出
        templateConfig.setServiceImpl("/templates/customServiceImpl.java.vm");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        // 开启逻辑删除
        strategy.setLogicDeleteFieldName("is_del");
        // 开启lombok
        strategy.setEntityLombokModel(true);
        // 开启rest模式
        strategy.setRestControllerStyle(true);
        // 继承基础controller
        strategy.setSuperControllerClass("com.gddlkj.example.web.rest.common.BaseController");
        // 继承基础entity
        strategy.setSuperEntityClass("com.gddlkj.example.model.domain.common.BaseEntity");
        strategy.setInclude(getTables().split(","));
        // entity跟基础entity公共字段
        strategy.setSuperEntityColumns("id", "create_date", "create_user", "update_date", "update_user");
        // 表前缀
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(null);
        mpg.execute();
    }

}
