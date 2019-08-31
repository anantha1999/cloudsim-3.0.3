/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim;

/**
 * The UtilizationModel interface needs to be implemented in order to provide a fine-grained control
 * over resource usage by a Cloudlet.
 * 
 * @author Anton Beloglazov
 * @since CloudSim Toolkit 2.0
 */
public interface WorkloadStorage {

	/**
	 * Returns the size of the file getting read or written at that particular time
	 * 
	 * @param time the time
	 * @param fileSize Total size of the file
	 * @return Sizeread/Sizewritten
	 */
	//double getSize(double time, long fileSize);
	
	/**
	 * Returns the number of reads or writes at that particular interval
	 * 
	 * @param time the time
	 * @param fileSize Total size of the file
	 * @return numberOfReads/numberOfWrites
	 */
	
	//int  getNumber(double time, long fileSize);
	
	/**
	 * Returns the total number of reads or writes at that particular interval
	 * 
	 * @param time the time
	 * @param fileSize Total size of the file
	 * @return totalNumberOfReads/totalNumberOfWrites
	 */
	
	int getTotalNumber();

}
