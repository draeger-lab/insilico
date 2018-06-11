package org.insilico.ui.services;

import java.net.URI;
import java.util.function.Predicate;

import org.insilico.ui.services.helper.IGraphicsRequest;


public interface IconUriProvider extends Predicate<IGraphicsRequest> {
    public URI getIconUri(IGraphicsRequest request);
}
