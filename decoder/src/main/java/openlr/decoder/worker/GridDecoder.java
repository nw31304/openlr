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
 *  Copyright (C) 2009-2012 TomTom International B.V.
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
package openlr.decoder.worker;

import openlr.OpenLRProcessingException;
import openlr.decoder.OpenLRDecoderProcessingException;
import openlr.decoder.OpenLRDecoderProcessingException.DecoderProcessingError;
import openlr.decoder.location.AffectedLinesImpl;
import openlr.decoder.location.DecodedGridLocation;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.decoder.worker.coverage.GridCoverage;
import openlr.location.Location;
import openlr.location.data.AffectedLines;
import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import openlr.map.MapDatabase;
import openlr.rawLocRef.RawLocationReference;

/**
 * The grid decoder.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author DLR e.V. (RE)
 */
public class GridDecoder extends AbstractDecoder {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Location doDecoding(final OpenLRDecoderProperties prop, final MapDatabase mdb,
			final RawLocationReference rawLocRef)
			throws OpenLRProcessingException {
		Location decoded;
		try {
			GeoCoordinates lowerleft = rawLocRef.getLowerLeftPoint();
			GeoCoordinates upperRight = rawLocRef.getUpperRightPoint();
			int ncols = rawLocRef.getNumberOfColumns();
			int nrows = rawLocRef.getNumberOfRows();
			AffectedLines result = null;
			if (prop.isCalcAffectedLines()) {
				GridCoverage coverage = new GridCoverage(lowerleft, upperRight, ncols, nrows);
				result = coverage.getAffectedLines(mdb);
			} else {
				result = AffectedLinesImpl.EMPTY;
			}
			decoded = new DecodedGridLocation(rawLocRef.getID(), result, ncols,
					nrows, lowerleft, upperRight);
		} catch (InvalidMapDataException e) {
			throw new OpenLRDecoderProcessingException(
					DecoderProcessingError.INVALID_MAP_DATA, e);
		}

		// return the decoded location
		return decoded;
	}
}
