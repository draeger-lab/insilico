package org.insilico.jsbml.core.editor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

public class DocumentTreeView {

    @Inject
    private SBMLDocument doc;

    @PostConstruct
    private void init(BorderPane parent) {
        TreeView<SBase> docTreeView = new TreeView<>(convertToTreeItem(doc, 2));

        parent.setCenter(docTreeView);
    }

    private TreeItem<SBase> convertToTreeItem(SBase elem, int expandLevels) {
        return convertToTreeItem(elem, 0, expandLevels);
    }

    private TreeItem<SBase> convertToTreeItem(SBase elem, int currentLevel, int expandLevels) {
        TreeItem<SBase> item = new TreeItem<SBase>(elem);
        item.setExpanded(currentLevel < expandLevels);

        if (elem.getChildCount() > 0) {
            List<Object> children = Collections.list(elem.children());
            List<TreeItem<SBase>> items = children.stream().filter(e -> e instanceof SBase)
                    .map(child -> convertToTreeItem((SBase) child, currentLevel + 1, expandLevels))
                    .collect(Collectors.toList());

            item.getChildren().addAll(items);
        }


        return item;
    }
}
