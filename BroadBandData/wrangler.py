#!/usr/bin/env python
import json
from geo_helper import turn_wgs84_into_osgb36, turn_osgb36_into_eastingnorthing

def blockify_geojson(jsonfile, data={}):
    """ Read in a geojson file
    
    Returns a dict where each key is the easting on a 1km UK OS National Grid
    Each easting key contains a dict where each key is a northing
    Each northing key contains any data from the json file
    """
    # Grab the file, deserialize the json and grab out the features
    geodata = json.load(open(jsonfile, 'r'))['features']
    
    for item in geodata:
        lon = item['geometry']['coordinates'][0]
        lat = item['geometry']['coordinates'][1]
        print item
        print lat, lon
        
        if lat and lon:
        
            # do conversion
            osgb36 = turn_wgs84_into_osgb36(lat, lon, 0)
            ngrid = turn_osgb36_into_eastingnorthing(osgb36[0], osgb36[1])
            
            easting = int(ngrid[0]/1000) #round the easting to the nearest kilometer
            northing = int(ngrid[1]/1000)
            
            # Set the data in place
            if data.get(easting, False) == False:
                data[easting] = {}
                
            if data.get(easting).get(northing, False) == False:
                data[easting][northing] = []
                
            data[easting][northing].append(item['properties'])
        
    return data
    
