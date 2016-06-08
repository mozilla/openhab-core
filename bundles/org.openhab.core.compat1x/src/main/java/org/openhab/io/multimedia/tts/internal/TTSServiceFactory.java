/**
 * Copyright (c) 2015-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.io.multimedia.tts.internal;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.openhab.io.multimedia.tts.TTSService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens for services that implement the old tts service interface
 * and registers an according service for each under the new interface.
 *
 * @author Tobias Bräutigam - Initial contribution and API (copied from
 *         ActionServiceFactory)
 */
public class TTSServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(TTSServiceFactory.class);

    private Map<String, ServiceRegistration<org.eclipse.smarthome.io.voice.TTSService>> delegates = new HashMap<>();
    private BundleContext context;

    private Map<TTSService, Map> ttsServices = new HashMap<>();

    public void activate(BundleContext context) {
        this.context = context;
        for (TTSService service : ttsServices.keySet()) {
            registerDelegateService(service, ttsServices.get(service));
        }
    }

    public void deactivate() {
        for (ServiceRegistration<org.eclipse.smarthome.io.voice.TTSService> serviceReg : delegates.values()) {
            serviceReg.unregister();
        }
        delegates.clear();
        this.context = null;
    }

    public void addTTSService(TTSService service, Map prop) {
        if (context != null) {
            registerDelegateService(service, prop);
        } else {
            ttsServices.put(service, prop);
        }
    }

    public void removeTTSService(TTSService service) {
        if (context != null) {
            unregisterDelegateService(service);
        }
    }

    private void registerDelegateService(TTSService ttsService, Map properties) {
        if (!delegates.containsKey(ttsService.getClass().getName())) {
            TTSServiceDelegate service = new TTSServiceDelegate(ttsService);
            Dictionary<String, Object> props = new Hashtable<String, Object>();
            if (properties != null && properties.containsKey("os")) {
                props.put("os", properties.get("os"));
            }
            ServiceRegistration<org.eclipse.smarthome.io.voice.TTSService> serviceReg = context
                    .registerService(org.eclipse.smarthome.io.voice.TTSService.class, service, props);
            delegates.put(ttsService.getClass().getName(), serviceReg);
        }
    }

    private void unregisterDelegateService(TTSService service) {
        if (delegates.containsKey(service.getClass().getName())) {
            ServiceRegistration<org.eclipse.smarthome.io.voice.TTSService> serviceReg = delegates
                    .get(service.getClass().getName());
            delegates.remove(service.getClass().getName());
            serviceReg.unregister();
        }
    }
}
