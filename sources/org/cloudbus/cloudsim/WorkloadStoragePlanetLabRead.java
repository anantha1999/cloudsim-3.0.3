package org.cloudbus.cloudsim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Class UtilizationModelPlanetLab.
 */
public class WorkloadStoragePlanetLabRead implements WorkloadStorage {
	
	
	/** The scheduling interval. */
	private double schedulingInterval;

	/** The data (5 min * 288 = 24 hours). */
	private final double[] data; 
	
	//private final double[] speed; //write mbps
	
	private double SizeRead = 0;
	
	private int numberOfReads;
	/**
	 * Instantiates a new utilization model PlanetLab.
	 * 
	 * @param inputPath the input path
	 * @throws NumberFormatException the number format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public WorkloadStoragePlanetLabRead(String inputPath, double schedulingInterval)
			throws NumberFormatException,
			IOException {
		data = new double[289];
		//speed = new double[289];
		setSchedulingInterval(schedulingInterval);
		BufferedReader input = new BufferedReader(new FileReader(inputPath));
		int i = 0;
		String line;
		while((line = input.readLine()) != null) {
			try {
			int length = line.split(" ",-1).length ;
			
			String[] workloadvalues = new String[length];
			if(length <= 1) {
				data[i] = 0;
				//speed[i] = 0;
			}
			else {
			workloadvalues = line.split(" ",-1);
			data[i] = Integer.valueOf(workloadvalues[4]);
			//speed[i] = Integer.valueOf(workloadvalues[5]);
			
			}
			}
			catch(Exception e) {
				data[i]=0;
				//speed[i] = 0;
			}
			i++;
			
		}
		
		
		
		//data[i - 1] = data[i - 2];
		//speed[i-1] = speed[i -2];
		input.close();
	}
		
	
	
	/**
	 * Instantiates a new utilization model PlanetLab with variable data samples.
	 * 
	 * @param inputPath the input path
	 * @param dataSamples number of samples in the file
	 * @throws NumberFormatException the number format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public WorkloadStoragePlanetLabRead(String inputPath, double schedulingInterval, int dataSamples)
			throws NumberFormatException,
			IOException {
		setSchedulingInterval(schedulingInterval);
		//speed = new double[dataSamples];
		data = new double[dataSamples];
		BufferedReader input = new BufferedReader(new FileReader(inputPath));
		int n = data.length;
	
		for (int i = 0; i < n - 1; i++) {
			try {
			int length = ((input.readLine()).split(" ",-1)).length;
			String[] workloadvalues = new String[length];
			if(length <= 1) {
				data[i] = 0;
				//speed[i] = 0;
			}
			else {
			workloadvalues = (input.readLine()).split(" ",-1);
			data[i] = Integer.valueOf(workloadvalues[4]);
			//speed[i] = Integer.valueOf(workloadvalues[5]);
			}
			}
			catch(Exception e) {
				data[i]=0;
				//speed[i] = 0;
			}
		}
		data[n - 1] = data[n - 2];
		//speed[n-1] = speed[n-2];
		input.close();
	}

	/*
	 * (non-Javadoc)
	 * @see cloudsim.power.UtilizationModel#getUtilization(double)
	 */
	@Override
	/*public double getSize(double time, long fileSize)  // size written at a given time.
	{	
		if(fileSize>0) {
		if (time % getSchedulingInterval() == 0) {
			if(data[(int) time / (int) getSchedulingInterval()]*speed[(int) time / (int) getSchedulingInterval()]<fileSize) {
				return data[(int) time / (int) getSchedulingInterval()]*speed[(int) time / (int) getSchedulingInterval()];
			}
			else {
				return fileSize;
			}
		}
		
		int time1 = (int) Math.floor(time / getSchedulingInterval());
		if(data[time1]*speed[time1] < fileSize) {
			this.SizeWritten = data[time1]*speed[time1]; // gives the size written. .
			return SizeWritten;
		}
		else {
			return fileSize;
		}
		}
		else {
			return 0;
		}
	}
	
	@Override
	// returns number of writes
	public int  getNumber(double time, long fileSize) {
		if (fileSize < 0 ) {	
			return 0;
		}
		
		int time1 = (int) Math.floor(time / getSchedulingInterval());
		@SuppressWarnings("deprecation")
		Double NoW = new Double(data[time1]);
		int numberOfWrites = NoW.intValue();
		return numberOfWrites;
	}*/
	
	//Gets total number of writes 
	/*@Override
	public int getTotalNumber( long fileSize ) {
		double size=0;
		double now = 0; //initial value of total number of writes
		for(int i=0; i<data.length;i++) {
			double numberOfWrites = data[i];
			double WriteSpeed = speed[i];
			
			if(Math.floor(size + numberOfWrites*WriteSpeed) < fileSize) { //checking if the writing again makes the size written greater than the file size
			now = now + numberOfWrites;
			size = size + numberOfWrites*WriteSpeed;
			}
			else if(size != fileSize) {
				now = now+1;
				break;
			}
			else {
				break;
			}
		}
		@SuppressWarnings("deprecation")
		Double tnw = new Double(now);
		int totalNumberOfWrites = tnw.intValue();
		return totalNumberOfWrites;
		
	}*/
	
	public int getTotalNumber() {
		int totalReads = 0;
		
		for(int i=0; i<data.length;i++) {
			Double NoR = new Double(data[i]);
			int readValue = NoR.intValue();
			totalReads = totalReads + readValue;
		}
		return totalReads;
	}

	/**
	 * Sets the scheduling interval.
	 * 
	 * @param schedulingInterval the new scheduling interval
	 */
	public void setSchedulingInterval(double schedulingInterval) {
		this.schedulingInterval = schedulingInterval;
	}

	/**
	 * Gets the scheduling interval.
	 * 
	 * @return the scheduling interval
	 */
	public double getSchedulingInterval() {
		return schedulingInterval;
	}
}
