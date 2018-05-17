package com.ist.cadillacpaltform.SDK.util.GradeTreeUtil;

import com.ist.cadillacpaltform.SDK.bean.Posm.AbstractGradeDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dearlhd on 2017/3/8.
 */
public class GradeTreeParser {

    static public List<AbstractModule> parseGradeTree (List<AbstractGradeDetail> details) {
        if (details == null || details.size() == 0) {
            return null;
        }

        // 解析第一层(module)
        List<AbstractModule> modules = new ArrayList<>();
        String moduleName = null;
        for (int i = 0; i < details.size(); i++) {
            AbstractGradeDetail tempDetail = details.get(i);
            if (moduleName == null || !moduleName.equals(tempDetail.getModuleName())) {
                moduleName = tempDetail.getModuleName();
                AbstractModule tempModule = new AbstractModule();
                tempModule.id = tempDetail.getModuleId();
                tempModule.name = moduleName;
                modules.add(tempModule);
            }
        }

        // 解析第二层(module-region)
        int last = 0;
        for (int i = 0; i < modules.size(); i++) {
            List<AbstractRegion> regions = new ArrayList<>();
            String regionName = null;
            for (int j = last; j < details.size(); j++) {
                AbstractGradeDetail tempDetail = details.get(j);
                if (!tempDetail.getModuleName().equals(modules.get(i).name)) {
                    last = j;
                    break;
                }

                if (regionName == null || !regionName.equals(tempDetail.getRegionName())) {
                    regionName = tempDetail.getRegionName();
                    AbstractRegion tempRegion = new AbstractRegion();
                    tempRegion.id = tempDetail.getRegionId();
                    tempRegion.name = regionName;
                    regions.add(tempRegion);
                }
            }
            modules.get(i).regions = regions;
        }

        // 解析第三层(module-region-item)
        last = 0;
        for (int i = 0; i < modules.size(); i++) {
            for (int j = 0; j < modules.get(i).regions.size(); j++) {
                AbstractRegion region = modules.get(i).regions.get(j);
                List<AbstractItem> items = new ArrayList<>();
                for (int k = last; k < details.size(); k++) {
                    if (region.name.equals(details.get(k).getRegionName())) {
                        AbstractItem tempItem = new AbstractItem();
                        AbstractGradeDetail tempDetail = details.get(k);
                        tempItem.id = tempDetail.getId();
                        tempItem.isFullMark = tempDetail.isFullMark();
                        tempItem.name = tempDetail.getQuestionItem();
                        tempItem.score = tempDetail.getScore();
                        tempItem.totalScore = tempDetail.getTotalScore();
                        tempItem.questionId = tempDetail.getQuestionId();
                        items.add(tempItem);
                    } else {
                        last = k;
                        break;
                    }
                }
                region.items = items;
            }
        }

        return modules;
    }

    public static List<Object> getVisibleNodes (List<AbstractModule> allModules) {
        List<Object> results = new ArrayList<>();
        for (int i = 0; i < allModules.size(); i++) {
            AbstractModule module = allModules.get(i);
            results.add(module);
            if (module.isExpand) {
                for (int j = 0; j < module.regions.size(); j++) {
                    AbstractRegion region = module.regions.get(j);
                    results.add(region);
                    if (region.isExpand) {
                        for (int k = 0; k < region.items.size(); k++) {
                            results.add(region.items.get(k));
                        }
                    }
                }
            }

        }

        return results;
    }
}
