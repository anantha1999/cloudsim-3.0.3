package org.cloudbus.cloudsim;

import java.util.List;


import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.File;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.WorkloadStorage;




public class CloudletIO extends Cloudlet {
	
	WorkloadStorage TotalNumberOfReads;
	WorkloadStorage TotalNumberOfWrites;
	
	
	
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
	
	public double getTotalNumberOfReads(long fileSize) {
		return getTotalNumberOfReads().getTotalNumber(fileSize);
	}
	
	public double getTotalNumberOfWrites(long fileSize) {
		return getTotalNumberOfWrites().getTotalNumber(fileSize);
	}
	
}

	
		