/**
 * Copyright (c) 2017-2018 The Semux Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.semux.core.bip39;

import org.semux.core.bip39.Dictionary;
import org.semux.core.bip39.Language;
import org.semux.core.bip39.MnemonicGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MnemonicGeneratorTest {

    @Test
    public void testHappyPath() {

        Dictionary dictionary;
        try {
            dictionary = new Dictionary(Language.english);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unknown dictionary");
        }

        MnemonicGenerator generator = new MnemonicGenerator();

        String phrase = generator.getWordlist(128, Language.english);

        byte[] seed = generator.getSeedFromWordlist(phrase, "", Language.english);
        byte[] seedAgain = generator.getSeedFromWordlist(phrase, "", Language.english);
        Assert.assertArrayEquals(seed, seedAgain);

        String[] words = phrase.split(" ");

        int index = dictionary.indexOf(words[0]);

        try {
            words[0] = "asdf";
            generator.getSeedFromWordlist(String.join(" ", words), "", Language.english);
            Assert.fail("Should not allow unknown word");
        } catch (IllegalArgumentException e) {
        }

        // try {
        // words[0] = dictionary.getWord((index + 1) % 2048);
        // generator.getSeedFromWordlist(String.join(" ", words), "", Language.english);
        // Assert.fail("Should not allow non-checksum'd words");
        // } catch (IllegalArgumentException e) {
        //
        // }
    }
}
