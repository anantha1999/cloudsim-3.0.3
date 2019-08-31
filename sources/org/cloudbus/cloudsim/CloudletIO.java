package org.cloudbus.cloudsim;

import java.util.List;


import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.File;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.WorkloadStorage;


/* extended class to include I/O workload */

public class CloudletIO extends Cloudlet {
	
	private WorkloadStorage TotalNumberOfReads;
	private WorkloadStorage TotalNumberOfWrites;
	
	
	
	public CloudletIO(
			final int cloudletId,
			final long cloudletLength,
			final int pesNumber,
			final long cloudletFileSize,
			final long cloudletOutputSize,
			final UtilizationModel utilizationModelCpu,
			final UtilizationModel utilizationModelRam,
			final UtilizationModel utilizationModelBw,
			final WorkloadStorage TotalNumberOfReads,
			final WorkloadStorage TotalNumberOfWrites) {
		   super(cloudletId,cloudletLength,pesNumber,cloudletFileSize,cloudletOutputSize,utilizationModelCpu,
				 utilizationModelRam,utilizationModelBw);
		   
		   setTotalNumberOfReads(TotalNumberOfReads);
		   setTotalNumberOfWrites(TotalNumberOfWrites);
	}
	
	public WorkloadStorage getTotalNumberOfReads() {
		return TotalNumberOfReads;
	}

	public void setTotalNumberOfReads(final WorkloadStorage TotalNumberOfReads) {
		this.TotalNumberOfReads = TotalNumberOfReads;
	}

	
	public WorkloadStorage getTotalNumberOfWrites() {
		return TotalNumberOfWrites;
	}

	public void setTotalNumberOfWrites(final WorkloadStorage TotalNumberOfWrites) {
		this.TotalNumberOfWrites = TotalNumberOfWrites;
	}
	
	public int getTotalNumberOfreads() {
		return getTotalNumberOfReads().getTotalNumber();
	}
	
	public int getTotalNumberOfwrites() {
		return getTotalNumberOfWrites().getTotalNumber();
	}
	
}

	
		
