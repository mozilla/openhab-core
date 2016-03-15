/**
 * Copyright (c) 2015-2016 Kai Kreuzer and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.io.multimedia.tts.internal;

import java.util.Set;

import org.eclipse.smarthome.io.audio.AudioFormat;
import org.eclipse.smarthome.io.audio.AudioSource;
import org.eclipse.smarthome.io.voice.TTSException;
import org.eclipse.smarthome.io.voice.TTSService;
import org.eclipse.smarthome.io.voice.Voice;

/**
 * This class serves as a mapping from the "old" org.openhab namespace to the
 * new org.eclipse.smarthome namespace for the action service. It wraps an
 * instance with the old interface into a class with the new interface.
 *
 * @author Tobias Bräutigam - Initial contribution and API
 */
public class TTSServiceDelegate implements TTSService {

    private org.openhab.io.multimedia.tts.TTSService service;

    public TTSServiceDelegate(org.openhab.io.multimedia.tts.TTSService service) {
        this.service = service;
    }

    public void say(String text, String voice, String outputDevice) {
        service.say(text, voice, outputDevice);
    }

    @Override
    public Set<Voice> getAvailableVoices() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<AudioFormat> getSupportedFormats() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AudioSource synthesize(String text, Voice voice, AudioFormat requestedFormat) throws TTSException {
        // TODO Auto-generated method stub
        return null;
    }

}
