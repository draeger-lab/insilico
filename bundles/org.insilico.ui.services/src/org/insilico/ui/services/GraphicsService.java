package org.insilico.ui.services;

import java.util.Map;
import java.util.Optional;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;

public interface GraphicsService {
    public interface Request {
        public void getContext();
        public Object getTarget();
        public Dimension2D getDimension();
        public Map<String, Object> getAttributes();
        public Object getAttribute(String key);
    }
    
    public Optional<Node> getGraphics(Request request);
    public Optional<Node> getGraphics(Object target, Dimension2D dimension, Map<String, Object> arguments);
}
