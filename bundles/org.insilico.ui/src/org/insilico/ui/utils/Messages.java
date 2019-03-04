package org.insilico.ui.utils;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.insilico.ui.messages"; //$NON-NLS-1$
	public static String add_module;
	public static String integration_failed;
	public static String integration_finished_restart;
	public static String reference_prefix;
	public static String uncompatible_data;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
