package org.cloudbus.cloudsim.examples.power.planetlab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelNull;
import org.cloudbus.cloudsim.UtilizationModelPlanetLabInMemory;
import org.cloudbus.cloudsim.UtilizationModelPlanetLabInMemoryBw;
import org.cloudbus.cloudsim.UtilizationModelPlanetLabInMemoryRam;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.examples.power.Constants;
import org.cloudbus.cloudsim.lists.VmList;

/**
 * A helper class for the running examples for the PlanetLab workload.
 * 
 * If you are using any algorithms, policies or workload included in the power package please cite
 * the following paper:
 * 
 * Anton Beloglazov, and Rajkumar Buyya, "Optimal Online Deterministic Algorithms and Adaptive
 * Heuristics for Energy and Performance Efficient Dynamic Consolidation of Virtual Machines in
 * Cloud Data Centers", Concurrency and Computation: Practice and Experience (CCPE), Volume 24,
 * Issue 13, Pages: 1397-1420, John Wiley & Sons, Ltd, New York, USA, 2012
 * 
 * @author Anton Beloglazov
 * @since Jan 5, 2012
 */
public class PlanetLabHelper {

	/**
	 * Creates the cloudlet list planet lab.
	 * 
	 * @param brokerId the broker id
	 * @param inputFolderName the input folder name
	 * @return the list
	 * @throws FileNotFoundException the file not found exception
	 */
	public static List<Cloudlet> createCloudletListPlanetLab(int brokerId, String inputFolderName)
			throws FileNotFoundException {
		List<Cloudlet> list = new ArrayList<Cloudlet>();

		long fileSize = 300;
		long outputSize = 300;
		//UtilizationModel utilizationModelNull = new UtilizationModelNull();

		File inputFolder = new File(inputFolderName);
		File[] files = inputFolder.listFiles();

		for (int i = 0; i < 1; i++) {
			Cloudlet cloudlet = null;
			try {
				cloudlet = new Cloudlet(
						i,
						Constants.CLOUDLET_LENGTH,
						Constants.CLOUDLET_PES,
						fileSize,
						outputSize,
						new UtilizationModelPlanetLabInMemory(
								files[i].getAbsolutePath(),
								Constants.SCHEDULING_INTERVAL), new UtilizationModelPlanetLabInMemoryRam(files[i].getAbsolutePath(), Constants.SCHEDULING_INTERVAL) , new UtilizationModelPlanetLabInMemoryBw(files[i].getAbsolutePath(), Constants.SCHEDULING_INTERVAL));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
			cloudlet.setUserId(brokerId);
			
			cloudlet.setVmId(i);
			list.add(cloudlet);
		}

		return list;
	}
/*
 *Storage capacity is taken into consideration here. 
 */
	public static List<Cloudlet> createCloudletListPlanetLab(int brokerId, String inputFolderName, List<Vm> vmlist, long fileIOSize)
			throws FileNotFoundException {
		List<Cloudlet> list = new ArrayList<Cloudlet>();

		long fileSize = fileIOSize;
		long outputSize = fileIOSize;
		//UtilizationModel utilizationModelNull = new UtilizationModelNull();

		File inputFolder = new File(inputFolderName);
		File[] files = inputFolder.listFiles();

		for (int i = 0; i < 1; i++) {
			Cloudlet cloudlet = null;
			try {
				cloudlet = new Cloudlet(
						i,
						Constants.CLOUDLET_LENGTH,
						Constants.CLOUDLET_PES,
						fileSize,
						outputSize,
						new UtilizationModelPlanetLabInMemory(
								files[i].getAbsolutePath(),
								Constants.SCHEDULING_INTERVAL), new UtilizationModelPlanetLabInMemoryRam(files[i].getAbsolutePath(), Constants.SCHEDULING_INTERVAL) , new UtilizationModelPlanetLabInMemoryBw(files[i].getAbsolutePath(), Constants.SCHEDULING_INTERVAL));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
			cloudlet.setUserId(brokerId);
			for(Vm vm : vmlist) {
				if(fileIOSize > vm.getSize()) {
					cloudlet.setVmId(-1);
				}
				else if(cloudlet.getNumberOfPes() > vm.getNumberOfPes()) {
					cloudlet.setVmId(-1);
				}
				else {
					vm.setSize(vm.getSize() - fileIOSize);
					cloudlet.setVmId(vm.getId());
				}
			}
			
			if(cloudlet.getVmId() != -1) {
				list.add(cloudlet);
			}
			else {
				Log.printLine("Not enough storage memory in any of the VMs to assign cloudlet "+i+"!");
			}
			
		}

		return list;
	
	} 
}
