package org.boc.help;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import sun.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class Env {
	public String getJavaSystem() {
		Properties props = System.getProperties();
		StringBuilder sb = new StringBuilder();
		String[] keys = { "os.arch", "os.name", "os.version", "sun.os.patch.level",
				"user.country", "user.dir", "user.home", "user.name", "user.language" };
		for (String key : keys)
			sb.append(key).append("=").append(props.getProperty(key)).append("\r\n");
		return sb.toString();
	}

	/**
	 * 获取widnows网卡的mac地址.
	 */
	public String getWindowsMACAddress() {
		StringBuilder mac = new StringBuilder();
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ipconfig /all");// windows下的命令，显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			int how = 0;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("physical address");// 寻找标示字符串[physical address]
				if (index >= 0) {// 找到了
					index = line.indexOf(":");// 寻找":"的位置
					if (index >= 0) {
						mac.append("网卡"+(++how)+":"+line.substring(index + 1).trim()+"\r\n");// 取出mac地址并去除2边空格
					}
				}
				index = line.toLowerCase().indexOf("ip address");// 寻找标示字符串[physical address]
				if (index >= 0) {// 找到了
					index = line.indexOf(":");// 寻找":"的位置
					if (index >= 0) {
						mac.append("--IP地址:"+line.substring(index + 1).trim()+"\r\n");
					}
				}
				index = line.toLowerCase().indexOf("subnet mask");// 寻找标示字符串[physical address]
				if (index >= 0) {// 找到了
					index = line.indexOf(":");// 寻找":"的位置
					if (index >= 0) {
						mac.append("--子网掩码:"+line.substring(index + 1).trim()+"\r\n");
					}
				}
				index = line.toLowerCase().indexOf("default gateway");// 寻找标示字符串[physical address]
				if (index >= 0) {// 找到了
					index = line.indexOf(":");// 寻找":"的位置
					if (index >= 0) {
						mac.append("--缺省网关:"+line.substring(index + 1).trim()+"\r\n");
					}
				}
				index = line.toLowerCase().indexOf("dns servers");// 寻找标示字符串[physical address]
				if (index >= 0) {// 找到了
					index = line.indexOf(":");// 寻找":"的位置
					if (index >= 0) {
						mac.append("--DNS服务:"+line.substring(index + 1).trim()+"\r\n");
					}
				}
			}
		} catch (IOException e) {
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {}
			bufferedReader = null;
			process = null;
		}

		return mac.toString();
	}
	
	public String getComputerName()  {
		InetAddress ind;
		String name = "";
		try {
			ind = InetAddress.getLocalHost();
			name = "计算机名称："+ind.getHostName()+"，IP地址："+ind.getHostAddress();
		} catch (UnknownHostException e) {
		}
		return name;
	}
	
	public String getMemoryInfo(){
		StringBuilder sb = new StringBuilder();
    int mb = 1024*1024;
//    long totalMemory = Runtime.getRuntime().totalMemory() / mb;//可使用内存
//    System.out.println("可使用内存:"+totalMemory);
//    long freeMemory = Runtime.getRuntime().freeMemory() / mb;//剩余内存
//    System.out.println("剩余内存:"+freeMemory);
//    long maxMemory = Runtime.getRuntime().maxMemory() / mb;//最大可使用内存
//    System.out.println("最大可使用内存:"+maxMemory);
    OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / mb;//总的物理内存
    sb.append("总的物理内存:"+totalMemorySize+"M\r\n");
    long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / mb;//剩余的物理内存
    sb.append("剩余的物理内存:"+freePhysicalMemorySize+"M\r\n");
    long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize())/mb;//已使用的物理内存
    sb.append("已使用的物理内存:"+usedMemory+"M\r\n");
    return sb.toString();
	}
	
	public String getPhisicalInfo(){
		StringBuilder sb = new StringBuilder();
    File[] roots = File.listRoots();//获取磁盘分区列表
    String p1="", p2="";
    for (int i = 0; i < roots.length; i++) {
    	if(roots[i].canRead()) p1 += roots[i]+"；";
    	else p2 += roots[i]+"；";    		      
    }
    sb.append("磁盘分区："+p1+"光盘分区:"+p2);
    
    return sb.toString();
	}



	public static void main(String[] args) {
		Env env = new Env();
		System.out.println(env.getJavaSystem());
		 String mac = env.getWindowsMACAddress();   
     System.out.println(mac);   
     System.out.println(env.getComputerName());
     System.out.println(env.getMemoryInfo());
     System.out.println(env.getPhisicalInfo());
	}
}
