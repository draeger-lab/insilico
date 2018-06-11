package org.insilico.ui.services;

import java.util.function.Predicate;

import org.insilico.ui.services.helper.IGraphicsRequest;

import javafx.scene.Node;

public interface GraphicsProvider extends Predicate<IGraphicsRequest> {
    public Node getGraphic(IGraphicsRequest request);
}
