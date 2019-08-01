package org.cloudbus.cloudsim.examples;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletIO;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.WorkloadStoragePlanetLabRead;
import org.cloudbus.cloudsim.WorkloadStoragePlanetLabWrite;

import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.UtilizationModelPlanetLabInMemory;
import org.cloudbus.cloudsim.UtilizationModelPlanetLabInMemoryBw;
import org.cloudbus.cloudsim.UtilizationModelPlanetLabInMemoryRam;
import org.cloudbus.cloudsim.UtilizationModelStochastic;
import org.cloudbus.cloudsim.Vm;

import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.examples.power.Constants;
import org.cloudbus.cloudsim.examples.power.planetlab.NonPowerAware;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;

import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

/**
 * 
 */

/**
 * @author Anantharam
 *
 */
public class workloadExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//1.0: Initialize the Cloudsim package. It should be called before creating any entities.
		int numUser = 1;
		Calendar cal = Calendar.getInstance();
		String inputFolder = CloudSimExample.class.getClassLoader().getResource("workload/planetlab/20110303")
				.getPath();
		
		File input = new File(inputFolder);
		File[] files = input.listFiles();
		
		boolean traceFlag = false;
		
		CloudSim.init(numUser, cal, traceFlag);
		
		//2.0: Create Datacenter(s) (Datacenter <<-- Datacentercharacteristics <<-- Hostlist <<-- Processing element list
		//(defines policy for VM allocation and scheduling
		Datacenter dc = CreateDataCenter();
		//3.0: Create broker
		DatacenterBroker dcb = null;
		
		try {
			dcb = new DatacenterBroker("DatacenterBroker1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//4.0: Create cloudlets(defines the workload)
		List<CloudletIO> cloudletList = new ArrayList<CloudletIO>();
		
		long cloudletLength = 40000;
		int pesNumber = 1;
		long cloudletFileSize = 300;
		long cloudletOutputSize = 400;
		//UtilizationModelStochastic Utilize = new UtilizationModelStochastic();
		
		
		for(int cloudletId=0;cloudletId<2;cloudletId++) {
	try {
		CloudletIO newcloudlet = new CloudletIO(cloudletId,cloudletLength,pesNumber,cloudletFileSize,cloudletOutputSize,new UtilizationModelPlanetLabInMemory(
				files[cloudletId].getAbsolutePath(),
				Constants.SCHEDULING_INTERVAL), new UtilizationModelPlanetLabInMemoryRam(files[cloudletId].getAbsolutePath(), Constants.SCHEDULING_INTERVAL) , new UtilizationModelPlanetLabInMemoryBw(files[cloudletId].getAbsolutePath(), Constants.SCHEDULING_INTERVAL),new WorkloadStoragePlanetLabRead(files[cloudletId].getAbsolutePath(), Constants.SCHEDULING_INTERVAL),
new WorkloadStoragePlanetLabWrite(files[cloudletId].getAbsolutePath(), Constants.SCHEDULING_INTERVAL));
	newcloudlet.setUserId(dcb.getId());
	cloudletList.add(newcloudlet);
	} catch (Exception e) {
		e.printStackTrace();
		System.exit(0);
	}
	
		}
		//5.0:	Create VMs(Define the procedure for Task scheduling algorithm)
		List<Vm> vmlist = new ArrayList<Vm>();
		long diskSize = 20000;
		int ram = 2000; 	
		int mips = 100;
		int bandwidth = 1000;
		int vCPU = 1;
		String VMM = "XEN";
		
		for(int id=0;id<10;id++) {
			Vm VirtualMachine = new Vm(id, dcb.getId(), mips, vCPU, ram, bandwidth, diskSize, VMM, new CloudletSchedulerSpaceShared());
			vmlist.add(VirtualMachine);
		}
		
		
		
		dcb.submitCloudletList(cloudletList);
		dcb.submitVmList(vmlist);
		
	//	dcb.assignCloudlets(vmlist,cloudletList);
				//6.0: Starts the simulation(automated process, handled through discrete event simulation engine)
				CloudSim.startSimulation();
				
				List<CloudletIO> finalCloudletExecutionResults = dcb.getCloudletReceivedList();
				
				CloudSim.stopSimulation();
				//7.0: Print results when simulation is over(Outputs).
				int cloudletNo = 0;
				for(CloudletIO c : finalCloudletExecutionResults)
				{
Log.printLine("Result of Cloudlet No. " + cloudletNo++);
Log.printLine();
Log.printLine("CloudletID : "+ c.getCloudletId() + ",VM_ID : " + c.getVmId() + ",status:" + c.getStatusString(c.getStatus()) + ",StartTime: " + c.getExecStartTime() + ",FinishTime: " + c.getFinishTime() + " ,Submission time: " + c.getSubmissionTime() + " ,No. of processors: "+ c.getNumberOfPes() + ",timeb/wevents: "+ CloudSim.getMinTimeBetweenEvents() + ", CPUruntime: "+ c.getActualCPUTime() + ",CloudletWaitingTime: "+ c.getWaitingTime());

for(double Time=c.getExecStartTime();Time<c.getFinishTime();Time=Time+1) {
	Log.printLine();
	Log.printLine("CPUutilization at time :"+Time+" is:"+c.getUtilizationOfCpu(Time));
	Log.printLine("RamUtilization at time "+ Time +":" + c.getUtilizationOfRam(Time));
	Log.printLine(" "+ Time +":" + c.getUtilizationOfRam(Time));
	Log.printLine();
}
	Log.printLine("total number of reads" +c.getTotalNumberOfReads(cloudletFileSize));
	Log.printLine("total number of writes" +c.getTotalNumberOfWrites(cloudletFileSize));
Log.printLine("***************************");

				}
		}
	
	private static Datacenter CreateDataCenter() 
	{
		List<Pe> peList = new ArrayList<Pe>();		
		
		PeProvisionerSimple pProvisioner = new PeProvisionerSimple(1000);
		
		Pe core1 = new Pe(0, pProvisioner);
		peList.add(core1);
		Pe core2 = new Pe(1, pProvisioner);
		peList.add(core2);
		Pe core3 = new Pe(2, pProvisioner);
		peList.add(core3);
		Pe core4 = new Pe(3, pProvisioner);
		peList.add(core4);
		/*Pe core5 = new Pe(4, pProvisioner);
		peList.add(core5);
		Pe core6 = new Pe(5, pProvisioner);
		peList.add(core6);
		Pe core7 = new Pe(6, pProvisioner);
		peList.add(core7);
		Pe core8 = new Pe(7, pProvisioner);
		peList.add(core8);*/
		
		List<Host> hostlist = new ArrayList<Host>();
		
		int ram = 8000;
		
		int bw = 8000;
		
		long storage = 100000;
		
		Host host1 = new Host(0, new RamProvisionerSimple(ram),new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
		hostlist.add(host1);
		
		Host host2 = new Host(1, new RamProvisionerSimple(ram),new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
		hostlist.add(host2);
		
		Host host3 = new Host(2, new RamProvisionerSimple(ram),new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
		hostlist.add(host3);
		
		Host host4 = new Host(3, new RamProvisionerSimple(ram),new BwProvisionerSimple(bw), storage, peList, new VmSchedulerSpaceShared(peList));
		hostlist.add(host4);
		
		String architecture = "x86";
		String os = "Linux";
		String vmm = "XEN";
		
		double timeZone = 5.0;
		double ComputecostPerSec = 3.0;
		double costPerMem = 1.0;
		double costPerStorage = 0.05;
		double costPerBw = 0.10;
		
		DatacenterCharacteristics dcCharacteristics = new DatacenterCharacteristics(architecture, os, vmm, hostlist, timeZone, ComputecostPerSec, costPerMem, costPerStorage, costPerBw);
		
		LinkedList<Storage> SANstorage = new LinkedList<Storage>();
		
		Datacenter dc = null;
		
		try {
			dc = new Datacenter("Datacenter1", dcCharacteristics, new VmAllocationPolicySimple(hostlist), SANstorage, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dc;
	}

}