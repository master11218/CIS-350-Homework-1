/**
 * @author Tanvir Ahmed
 * Testing class for GPXcalculator.
 * CIS 350
 * 02/02/12
 */
package edu.upenn.cis350.gpx;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import org.junit.Test;


public class GPXcalculatorTest {
	//Test #1
	@Test
	public void testNullInput() {
		//If GPXtrk null, it should return a -1
		assertTrue(GPXcalculator.calculateDistanceTraveled(null) == -1);
	}

	//Test #2
	@Test
	public void testGPXtrkNullSeg() {
		//If GPXtrk has a null instead of a GPStrkseg object, it should return a -1
		GPXtrk trackObjWithNullInsteadOfArrayList = new GPXtrk("Test", null);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackObjWithNullInsteadOfArrayList) == -1);
	}

	//Test #3
	@Test
	public void testGPXtrkEmptySegArray(){
		//If GPXtrk has an ArrayList without any GPStrkseg objects in it, it should return a -1
		ArrayList<GPXtrkseg> listOfGPXtrkseg = new ArrayList<GPXtrkseg>();
		GPXtrk trackObjWithEmptyArrayList = new GPXtrk("Test", listOfGPXtrkseg);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackObjWithEmptyArrayList) == -1);
	}

	//Test #4
	@Test
	public void testGPXtrkOnlyHasOneNullElement(){
		//test if GPXtrk only has 1 element, but it's null. should be 0
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(null);
		GPXtrk trackWithOneNullSegment = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackWithOneNullSegment) == 0);
	}

	//Test #5
	@Test
	public void testGPXtrkHasNullSegment(){
		//test if GPXtrk has multiple elements, but one of them is null. should count null as 0, but still return distance for non-null item(s).
		GPXtrkpt firstPoint = new GPXtrkpt(10, 10, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(40, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(firstPoint);
		points.add(secondPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(null);
		segmentList.add(segment);

		GPXtrk trackWithTwoSegmentsButOneIsNull = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackWithTwoSegmentsButOneIsNull) == 30);
	}

	//Test #6
	@Test
	public void testGPXtrksegHasNoTrks1(){
		//test if GPXtrk has 1 segment, but that segment has no trkpts. Should be 0.
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 0);
	}

	//Test #7
	@Test
	public void testGPXtrksegHasNoTrks2(){
		//test if GPXtrk has 1 segment, but one segment has no trkpts. The segment with no trkpts should be skipped, but the other segments should be counted. Should be 30.
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		ArrayList<GPXtrkpt> points2 = new ArrayList<GPXtrkpt>();
		GPXtrkpt firstPoint = new GPXtrkpt(10, 10, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(40, 10, new Date());
		points2.add(firstPoint);
		points2.add(secondPoint);

		GPXtrkseg segmentThatShouldBeZero = new GPXtrkseg(points);
		GPXtrkseg normalSegment = new GPXtrkseg(points2);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segmentThatShouldBeZero);
		segmentList.add(normalSegment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 30);
	}

	//Test #8
	@Test
	public void testGPXtrksegHasOneTrk(){
		//test if GPXtrk has 1 segment that has 1 trkpt. Should be 0.
		GPXtrkpt firstPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 0);
	}

	//Test #9
	@Test
	public void testGPXtrksegHasOneTrk2(){
		//test if GPXtrk has 2 segments, but one of them has 1 trkpt. Should count the segment with 1 trkpt as 0, but still count the other segment. Should be 30.
		GPXtrkpt firstPoint = new GPXtrkpt(10, 10, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(40, 10, new Date());

		ArrayList<GPXtrkpt> points2 = new ArrayList<GPXtrkpt>();
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();

		points.add(firstPoint);
		points2.add(firstPoint);
		points2.add(secondPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		GPXtrkseg segment2 = new GPXtrkseg(points2);

		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);
		segmentList.add(segment2);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 0);
	}

	//Test #10
	@Test
	public void testGPXtrksegHasGPXtrkThatIsNull(){
		//test if GPXtrk has 1 segment with a trk that is null. Should be 0.
		GPXtrkpt firstPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(null);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 0);
	}

	//Test #11
	@Test
	public void testGPXtrksegHasGPXtrkThatIsNull2(){
		//test if GPXtrk has 2 segments, but one of the segments has a trk that is null. Should count the null-track segment as 0, but still count the other one. Should be 30.
		GPXtrkpt firstPoint = new GPXtrkpt(10, 10, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(40, 10, new Date());

		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		ArrayList<GPXtrkpt> points2 = new ArrayList<GPXtrkpt>();

		points.add(null);
		points.add(firstPoint);
		points2.add(firstPoint);
		points2.add(secondPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		GPXtrkseg segment2 = new GPXtrkseg(points2);

		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);
		segmentList.add(segment2);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 0);
	}

	//Test #12
	@Test
	public void testGPXtrkWithInvalidLongitudePos(){
		//If there's any trk whose longitude is over 90, the distance traveled for the containing GPXtrkseg should be considered 0.
		GPXtrkpt firstPoint = new GPXtrkpt(91, 10, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 0);
	}

	//Test #13
	@Test
	public void testGPXtrkWithValidEdgeLongitudePos(){
		//If there's any trk whose longitude is 90, it should be treated normally. In this case, it should be 80.
		GPXtrkpt firstPoint = new GPXtrkpt(90, 10, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 80);
	}

	//Test #14
	@Test
	public void testGPXtrkWithInvalidLongitudeNeg(){
		//If there's any trk whose longitude is less than -90, the distance traveled for the containing GPXtrkseg should be considered 0.
		GPXtrkpt firstPoint = new GPXtrkpt(-91, 10, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 0);
	}
	
	//Test #15
	@Test
	public void testGPXtrkWithValidEdgeLongitudeNeg(){
		//If there's any trk whose longitude is -90, it should be treated normally. In this case, it should be 80.
		GPXtrkpt firstPoint = new GPXtrkpt(-90, 10, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 80);
	}
	
	//Test #16
	@Test
	public void testGPXtrkWithValidLongitudeZero(){
		//If there's any trk whose longitude is 0, it should be treated normally. In this case, it should be 10.
		GPXtrkpt firstPoint = new GPXtrkpt(0, 10, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 10);
	}

	//Test #17
	@Test
	public void testGPXtrkWithInvalidLatitudePos(){
		//If there's any trk whose latitude is more than 90, the distance traveled for the containing GPXtrkseg should be considered 0.
		GPXtrkpt firstPoint = new GPXtrkpt(10, 91, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 0);
	}
	
	//Test #18
	@Test
	public void testGPXtrkWithValidEdgeLatitudePos(){
		//If there's any trk whose latitude is 90, it should be treated normally. In this case, it should be 80.
		GPXtrkpt firstPoint = new GPXtrkpt(10, 90, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 80);
	}

	//Test #19
	@Test
	public void testGPXtrkWithInvalidLatitudeNeg(){
		//If there's any trk whose latitude is less than 90, the distance traveled for the containing GPXtrkseg should be considered 0.
		GPXtrkpt firstPoint = new GPXtrkpt(10, -91, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 0);
	}
	
	//Test #20
	@Test
	public void testGPXtrkWithValidEdgeLatitudeNeg(){
		//If there's any trk whose latitude is -90, it should be treated normally. In this case, it should be 80.
		GPXtrkpt firstPoint = new GPXtrkpt(10, -90, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 80);
	}
	
	//Test #21
	@Test
	public void testGPXtrkWithValidLatitudeZero(){
		//If there's any trk whose latitude is 0, it should be treated normally. In this case, it should be 10.
		GPXtrkpt firstPoint = new GPXtrkpt(10, 0, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 10);
	}

	//Test #22
	@Test
	public void testGPXsegWithSamePointTwice(){
		//If there's 1 segment with 2 points, but the points are the same. The distance should be 0.
		GPXtrkpt firstPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(firstPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 0);
	}

	//Test #23
	@Test
	public void testRegularCaseWithOneSegment(){
		//Should return correct Euclidean distance.
		GPXtrkpt firstPoint = new GPXtrkpt(10, 40, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 30);
	}

	//Test #24
	@Test
	public void testRegularCaseWithTwoSegments(){
		//Should return correct Euclidean distance.
		GPXtrkpt firstPoint = new GPXtrkpt(10, 40, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(secondPoint);
		points.add(firstPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();

		//I'll just add the same segment twice. should be double the Euclidean distance from the last one
		segmentList.add(segment);
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 60);
	}
	
	//Test #25
	@Test
	public void testRegularCaseWithOneSegmentThreePoints(){
		//Should return correct Euclidean distance.
		GPXtrkpt firstPoint = new GPXtrkpt(10, 40, new Date());
		GPXtrkpt secondPoint = new GPXtrkpt(10, 10, new Date());
		GPXtrkpt thirdPoint = new GPXtrkpt(0, 0, new Date());
		ArrayList<GPXtrkpt> points = new ArrayList<GPXtrkpt>();
		points.add(firstPoint);
		points.add(secondPoint);
		points.add(thirdPoint);

		GPXtrkseg segment = new GPXtrkseg(points);
		ArrayList<GPXtrkseg> segmentList = new ArrayList<GPXtrkseg>();
		segmentList.add(segment);

		GPXtrk trackToTest = new GPXtrk("Test", segmentList);
		assertTrue(GPXcalculator.calculateDistanceTraveled(trackToTest) == 50);
	}
}