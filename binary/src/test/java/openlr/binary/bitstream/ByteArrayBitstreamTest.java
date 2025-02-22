/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License and the extra
 *  conditions for OpenLR. (see openlr-license.txt)
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

/**
 *  Copyright (C) 2009,2010 TomTom International B.V.
 *
 *   TomTom (Legal Department)
 *   Email: legal@tomtom.com
 *
 *   TomTom (Technical contact)
 *   Email: openlr@tomtom.com
 *
 *   Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 *   the Netherlands
 */
package openlr.binary.bitstream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import openlr.binary.bitstream.impl.ByteArrayBitstreamInput;

import org.testng.annotations.Test;

/**
 * The Class ByteArrayBitstreamTest.
 */
public class ByteArrayBitstreamTest {
	
	/**
	 * Test empty bitstream.
	 */
	@Test
	public final void testEmptyBitstream() {
		try {
			new ByteArrayBitstreamInput(new byte[0]);
		} catch (BitstreamException be) {
			assertEquals(be.getErrorCode(),
					BitstreamException.BitstreamErrorType.ENDOFDATA);
		}
	}

	/**
	 * Test byte array bitstream.
	 */
	@Test
	public final void testByteArrayBitstream() {
		File dataFile01 = new File("src/test/resources/test01.stream");
		File dataFile02 = new File("src/test/resources/test02.stream");
		if (!dataFile01.exists() || !dataFile02.exists()) {
			fail("resource is missing");
		}
		byte[] data01 = new byte[(int) dataFile01.length()];
		byte[] data02 = new byte[(int) dataFile02.length()];
		try {
			FileInputStream fr01 = new FileInputStream(dataFile01);
			fr01.read(data01);
			FileInputStream fr02 = new FileInputStream(dataFile01);
			fr02.read(data02);
		} catch (FileNotFoundException e) {
			fail("missing resource", e);
		} catch (IOException e) {
			fail("reading resource failed", e);
		}
		
		try {
			new ByteArrayBitstreamInput(data01);			
		} catch (BitstreamException be) {
			fail("bistream failure", be);
		}
		
		try {
			new ByteArrayBitstreamInput(data02);			
		} catch (BitstreamException be) {
			fail("bistream failure", be);
		}
	}

}
