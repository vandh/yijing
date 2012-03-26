package org.boc.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.boc.db.YiJing;
import org.boc.db.ly.Liuyao;
import org.boc.db.qm.QiMen;
import org.boc.db.qm.QiMen2;
import org.boc.util.Messages;
import org.boc.util.Public;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlProc {
	private static XmlProc xml = new XmlProc();
	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder parser;
	
	private XmlProc() {}
	public static XmlProc getInstance() {
		return xml;
	}
	/**
	 * 从文件中创建一个Document
	 */
	public Document getDocument(String filename) throws Exception {
		return getDocument(getFile(filename));
	}
	public Document getDocument(File file) throws Exception {
		parser = factory.newDocumentBuilder();		
		if(!file.exists()) {
			return null;
		}
//this.getClass().getResource("/").getPath();
//		if(path.startsWith("/")) path = path.substring(1);		
//		if(path.lastIndexOf("bin")!=-1) path = path.substring(0, path.length()-4);
//		System.out.println(path+Public.DB+"/"+filepath);
		return parser.parse(file);
	}
	
	public File getFile(String filename) {
		return new File(Public.QMXXDZ+"/"+filename);
	}
	/**
	 * 创建一个空的Document
	 */
	public Document createNewDocument() throws Exception {
		Document doc = null;
		DocumentBuilderFactory factory;				

		factory = DocumentBuilderFactory.newInstance();
		doc = factory.newDocumentBuilder().newDocument();		
		return doc;
	}
	/**
	 * 创建根节点元素
	 */
	public Element createRootElement(Document doc, String rootName) throws Exception {	
		Element root = doc.createElement(rootName);
		doc.appendChild(root); 
		return root;
	}
	
	/**
	 * 创建子节点
	 */
	public Element createElement(Document doc, Element element, String nodeName) throws Exception {	
		Element newNode = doc.createElement(nodeName);
		element.appendChild(newNode); 
		return newNode;
	}
	
	/**
	 * 输出Document到xml文件
	 */
	public void save2File(Document doc, File file) {
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(file);
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			java.util.Properties properties = t.getOutputProperties();
			properties.setProperty(OutputKeys.INDENT, "yes"); 
			properties.setProperty(OutputKeys.ENCODING, "GBK");
			t.setOutputProperties(properties);
			t.transform(doms, sr);
		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
			Messages.error("TreePanel(" + tce.getMessage() + ")");
		} catch (TransformerException te) {
			te.printStackTrace();
			Messages.error("TreePanel(" + te.getMessage() + ")");
		}
	}

	/**
	 * 从xml文件中读取所有元素，支持3级元素的 内容处理。文档中空格和换行原样输出；
	 * <天干><甲><描述>...</描述></甲></天干>:解析后输出“甲： 描述:...”
	 */
	public Map<String,String> readFromFile2(String filepath) throws Exception {
		Document doc = this.getDocument(filepath);
		if(doc==null) return new HashMap<String,String>();
		StringBuilder eString =  new StringBuilder();  //用来存放某个元素的内容
		Map<String,String> xmlMap = new HashMap<String,String>();  //用来存元素的键值对		
		Node element = doc.getDocumentElement();
		//String ename = element.getNodeName();		//得到根元素				
		
		NodeList child = element.getChildNodes();  
		for (int i = 0; i < child.getLength(); i++) {			
			Node node2 = child.item(i);						//得到2级元素
			if("#text".equals(node2.getNodeName())) continue;
			eString.delete(0, eString.length());
			NodeList child3 = node2.getChildNodes();  //得到3级元素，最多3级
			for (int j = 0; j < child3.getLength(); j++) {
				Node node3 = child3.item(j);						
				if("#text".equals(node3.getNodeName())) continue;
				eString.append(node3.getNodeName()+"：");	
				String text = node3.getTextContent().trim(); 								//3级元素名称
				eString.append(text.replaceAll("\n", QiMen2.HH)); //给3级元素的内容加上换行符
			}
			xmlMap.put(node2.getNodeName(), eString.toString());
		}
		return xmlMap;
	}
	
	/**
	 * 从xml文件中读取所有元素，支持2级元素的 内容处理。文档中空格和换行原样输出；
	 * <天干><甲>...</甲></天干>:解析后输出“甲： ...”
	 */
	public Map<String,String> readFromFile(String filepath) throws Exception {
		Document doc = this.getDocument(filepath);
		if(doc==null) return new HashMap<String,String>();
		StringBuilder eString =  new StringBuilder();  //用来存放某个元素的内容
		Map<String,String> xmlMap = new HashMap<String,String>();  //用来存元素的键值对		
		Node element = doc.getDocumentElement();
		//String ename = element.getNodeName();		//得到根元素				
		
		NodeList child = element.getChildNodes();  
		for (int i = 0; i < child.getLength(); i++) {			
			Node node2 = child.item(i);						//得到2级元素名称
			eString.delete(0, eString.length());
			String text = node2.getTextContent().trim(); 								
			text = text.replaceAll("\n", QiMen2.HH); //给2级元素的内容加上换行符			
			xmlMap.put(node2.getNodeName(), text);
		}
		return xmlMap;
	}
	/**
	 * 从启动配置文件中读取启动选项，供Main.java调用
	 */
	public static void loadFromOnFile() {
		loadLiuyaoFile();
		loadQimenFile();
	}
	private static void loadLiuyaoFile() {
		try {
			if(!(new File(Public.LYQDSZ).exists())) return;
			Document doc = xml.getDocument(new File(Public.LYQDSZ));	
			Node element = doc.getDocumentElement();			
			NodeList child = element.getChildNodes();  
			for (int i = 0; i < child.getLength(); i++) {			
				Node node2 = child.item(i);						//得到2级元素名称
				String text = node2.getTextContent();
				if(text.trim().equals("")) continue;
				String key = node2.getNodeName();
				int val = Integer.valueOf(text); 								
				//System.out.println(key+"="+val);
				if(key.equals("ma")) Liuyao.MA = val==1;				
				else if(key.equals("jxge")) Liuyao.JXGE = val==1;
				else if(key.equals("wsxq")) Liuyao.WSXQ = val==1;
				else if(key.equals("tip")) Liuyao.TIP = val==1;
				else if(key.equals("tool")) Liuyao.TOOL = val==1;
				else if(key.equals("input")) Liuyao.INPUT = val==1;
				else if(key.equals("head")) Liuyao.HEAD = val==1;
				else if(key.equals("io")) Liuyao.IO = val==1;
				else if(key.equals("left")) Liuyao.LEFT = val==1 ? Liuyao.LEFTMAX : Liuyao.LEFTMIN;
				else if(key.equals("now")) Liuyao.NOW = val==1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void loadQimenFile() {
		try {
			if(!(new File(Public.QMQDSZ).exists())) return;
			Document doc = xml.getDocument(new File(Public.QMQDSZ));	
			Node element = doc.getDocumentElement();			
			NodeList child = element.getChildNodes();  
			for (int i = 0; i < child.getLength(); i++) {			
				Node node2 = child.item(i);						//得到2级元素名称
				String text = node2.getTextContent();
				if(text.trim().equals("")) continue;
				String key = node2.getNodeName();
				int val = Integer.valueOf(text); 								
				//System.out.println(key+"="+val);
				if(key.equals("ma")) QiMen2.MA = val==1;				
				else if(key.equals("zf")) QiMen2.ZF = val==1;
				else if(key.equals("jxge")) QiMen2.JXGE = val==1;
				else if(key.equals("rb")) QiMen2.RB = val==0;
				else if(key.equals("sgky")) QiMen2.SGKY = val==1;
				else if(key.equals("td")) QiMen2.TD = val==1;
				else if(key.equals("wsxq")) QiMen2.WSXQ = val==1;
				else if(key.equals("kg")) QiMen2.KG = val==1;
				else if(key.equals("xchm")) QiMen2.XCHM = val==1;
				else if(key.equals("tip")) QiMen2.TIP = val==1;
				else if(key.equals("jtxms")) QiMen2.JTXMS = val==1;
				else if(key.equals("xmhw")) QiMen2.XMHW = val==1? 0 : 1;
				else if(key.equals("huo")) QiMen2.HUO = val==1;
				else if(key.equals("tool")) QiMen2.TOOL = val==1;
				else if(key.equals("sj")) QiMen2.SJ = val==1;
				else if(key.equals("input")) QiMen2.INPUT = val==1;
				else if(key.equals("head")) QiMen2.HEAD = val==1;
				else if(key.equals("io")) QiMen2.IO = val==1;
				else if(key.equals("left")) QiMen2.LEFT = val==1 ? QiMen2.LEFTMAX : QiMen2.LEFTMIN;
				else if(key.equals("calendar")) QiMen2.CALENDAR = val==1;
				else if(key.equals("yang")) QiMen2.YANG = val==1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getXmlhead() {
		return "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	}
	
	/**
	 * 将保存的文件输出为缺省的xml文件
	 * 1. 十干克应：  printInner2XmlFile("十干克应",50,138);
	 * 2. 八门克应：  printInner2XmlFile("八门克应",170,247);
	 * 3. 门干克应: printInner2XmlFile("门干克应",250,328);
	 * 4. 星门克应：  printInner2XmlFile("星门克应",330,417);
	 * 5. 三诈五鬼九遁： printInner2XmlFile("三诈五鬼九遁",420,436);
	 * 6. 神门克应：  printInner2XmlFile("神门克应",470,533);
	 * 7. 后天八卦： printInner2XmlFile(QiMen.JIUGONGINFO,"后天八卦",1,9);
	 * 8. 十二神将：printInner2XmlFile(QiMen2.SHENJ,"十二神将",1,12);
	 * 9. 地支： printInner2XmlFile(QiMen2.ZI,"地支",1,12);
	 * 10. 八门：printInner2XmlFile(QiMen2.MEN,"八门",1,9);
	 * 11. 九星：printInner2XmlFile(QiMen2.XING,"九星",1,9);
	 * 12. 九宫：printInner2XmlFile(QiMen2.GUA,"九宫",1,9);
	 * 13. 八神：printInner2XmlFile(QiMen2.SHEN,"八神",1,8);
	 * 14. 天干： printInner2XmlFile(QiMen2.GAN,"天干",1,10);
	 * 15. 吉凶格：printInner2XmlFile("吉凶格",1,33);
	 */
//	public static void printInner2XmlFile(String fname, int start, int end) {
//		printInner2XmlFile(QiMen.gGejuDesc, fname, start, end);
//	}
//	public static void printInner2XmlFile(String[][] arr,String fname, int start, int end) {		
//		PrintWriter pw = null;
//		try {			
//			pw = new PrintWriter(xml.getFile(fname+".xml"));
//			pw.println(xml.getXmlhead());
//			pw.println("<"+fname+">");
//			for(int i=start; i<=end; i++) {
//				if(arr[i][1]==null) continue;
//				pw.println("  <"+arr[i][0]+">");
//				pw.println(arr[i][1].replaceAll(QiMen.HH, "\r\n"));
////				pw.println("请修改程序文件｛"+Public.QMXXDZ+"/"+fname+".xml｝");
////				pw.println("如需帮助，请参考博文《奇门软件26大功能使用详解附详图并免费下载》");
////				pw.println("博文地址：http://blog.sina.com.cn/s/blog_93c2f44d01012vrw.html");
//				pw.println("  </"+arr[i][0]+">");
//			}
//			pw.println("</"+fname+">");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			pw.close();
//		}
//	}
	
	/**
	 * 九星值时克应：printInner2XmlFile(QiMen2.JXZS,"九星值时克应",1,9);
	 */
//	public static void printJxzs() {		
//		PrintWriter pw = null;
//		try {			
//			//1. 生成十干克应.xml文件
//			pw = new PrintWriter(xml.getFile("九星值时克应.xml"));
//			pw.println(xml.getXmlhead());
//			pw.println("<九星值时克应>");
//			for(int i=1; i<=9; i++) {
//				for(int j=1; j<=12; j++) {
//					pw.println("  <"+"天"+QiMen.jx1[i]+"值"+YiJing.DIZINAME[j]+"时>");
//					pw.println(QiMen2.JXZS[i][j].replaceAll(QiMen.HH, "\r\n"));
////					pw.println("请修改程序文件｛"+Public.QMXXDZ+"/九星值时克应.xml｝");
////					pw.println("如需帮助，请参考博文《奇门软件26大功能使用详解附详图并免费下载》");
////					pw.println("博文地址：http://blog.sina.com.cn/s/blog_93c2f44d01012vrw.html");
//					pw.println("  </"+"天"+QiMen.jx1[i]+"值"+YiJing.DIZINAME[j]+"时>");
//				}				
//			}
//			pw.println("</九星值时克应>");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			pw.close();
//		}
//	}
	/**
	 * 在内置与定制之间切换
	 */
	public static void loadFromSysFile() {
		QiMen2.cp(QiMen2.GAN2, QiMen2.GAN);			//1. 天干
		QiMen2.cp(QiMen2.ZI2, QiMen2.ZI);				//2. 地支
		QiMen2.cp(QiMen2.MEN2, QiMen2.MEN);			//3. 八门
		QiMen2.cp(QiMen2.XING2, QiMen2.XING);		//4. 九星
		QiMen2.cp(QiMen2.SHEN2, QiMen2.SHEN);		//5. 八神
		QiMen2.cp(QiMen2.GUA2, QiMen2.GUA);			//6. 九宫
		//7. 十干克应、八门克应、门干克应、星门克应、三诈五鬼九遁、神门克应、吉凶格
		QiMen2.cp(QiMen.gGejuDesc2, QiMen.gGejuDesc);
		QiMen2.cp(QiMen.JIUGONGINFO2, QiMen.JIUGONGINFO);//13. 后天八卦
		QiMen2.cp(QiMen2.SHENJ2, QiMen2.SHENJ);	//14. 十二神将
		QiMen2.cp(QiMen2.JXZS2, QiMen2.JXZS);		//15. 九星值时克应
		QiMen2.cp(QiMen2.GAN2, QiMen2.GAN);
		QiMen2.cp(QiMen2.GAN2, QiMen2.GAN);
		QiMen2.cp(QiMen2.GAN2, QiMen2.GAN);
		QiMen2.cp(QiMen2.GAN2, QiMen2.GAN);
	}
	/**
	 * 驱动时加载自定义的文件
	 */
  public static void loadFromXmlFile() {
  	try{
	    //1. 天干
	    Map<String,String> map = xml.readFromFile("天干.xml");
	    if(map.size()>1) {
		    for(int i=1; i<=10; i++) {
		    	QiMen2.GAN[i][1] = map.get(QiMen2.GAN[i][0])==null?"":map.get(QiMen2.GAN[i][0]);
		    }
	    }
	    //2. 地支
	    map = xml.readFromFile("地支.xml");
	    if(map.size()>1)
	    for(int i=0; i<QiMen2.ZI.length; i++) {
	    	QiMen2.ZI[i][1] = map.get(QiMen2.ZI[i][0])==null?"":map.get(QiMen2.ZI[i][0]);
	    }
	    //3. 八门
	    map = xml.readFromFile("八门.xml");
	    if(map.size()>1)
	    for(int i=0; i<QiMen2.MEN.length; i++) {
	    	QiMen2.MEN[i][1] = map.get(QiMen2.MEN[i][0])==null?"":map.get(QiMen2.MEN[i][0]);
	    }
	    //4. 九星
	    map = xml.readFromFile("九星.xml");
	    if(map.size()>1)
	    for(int i=0; i<QiMen2.XING.length; i++) {
	    	QiMen2.XING[i][1] = map.get(QiMen2.XING[i][0])==null?"":map.get(QiMen2.XING[i][0]);
	    }
	    //5. 八神
	    map = xml.readFromFile("八神.xml");
	    if(map.size()>1)
	    for(int i=0; i<QiMen2.SHEN.length; i++) {
	    	QiMen2.SHEN[i][1] = map.get(QiMen2.SHEN[i][0])==null?"":map.get(QiMen2.SHEN[i][0]);
	    }
	    //6. 九宫
	    map = xml.readFromFile("九宫.xml");
	    if(map.size()>1)
	    for(int i=0; i<QiMen2.GUA.length; i++) {
	    	QiMen2.GUA[i][1] = map.get(QiMen2.GUA[i][0])==null?"":map.get(QiMen2.GUA[i][0]);
	    }
	    //7. 十干克应
	    map = xml.readFromFile("十干克应.xml");
	    if(map.size()>1)
	    for(int i=50; i<=138; i++) {
	    	QiMen.gGejuDesc[i][1] = map.get(QiMen.gGejuDesc[i][0])==null?"":map.get(QiMen.gGejuDesc[i][0]);
	    }
	    //8. 八门克应
	    map = xml.readFromFile("八门克应.xml");
	    if(map.size()>1)
	    for(int i=170; i<=247; i++) {
	    	QiMen.gGejuDesc[i][1] = map.get(QiMen.gGejuDesc[i][0])==null?"":map.get(QiMen.gGejuDesc[i][0]);
	    }
	    //9. 门干克应
	    map = xml.readFromFile("门干克应.xml");
	    if(map.size()>1)
	    for(int i=250; i<=328; i++) {
	    	QiMen.gGejuDesc[i][1] = map.get(QiMen.gGejuDesc[i][0])==null?"":map.get(QiMen.gGejuDesc[i][0]);
	    }
	    //10. 星门克应
	    map = xml.readFromFile("星门克应.xml");
	    if(map.size()>1)
	    for(int i=330; i<=417; i++) {
	    	QiMen.gGejuDesc[i][1] = map.get(QiMen.gGejuDesc[i][0])==null?"":map.get(QiMen.gGejuDesc[i][0]);
	    }
	    //11. 三诈五鬼九遁
	    map = xml.readFromFile("三诈五鬼九遁.xml");
	    if(map.size()>1)
	    for(int i=420; i<=436; i++) {
	    	QiMen.gGejuDesc[i][1] = map.get(QiMen.gGejuDesc[i][0])==null?"":map.get(QiMen.gGejuDesc[i][0]);
	    }
	    //12. 神门克应
	    map = xml.readFromFile("神门克应.xml");
	    if(map.size()>1)
	    for(int i=470; i<=533; i++) {
	    	QiMen.gGejuDesc[i][1] = map.get(QiMen.gGejuDesc[i][0])==null?"":map.get(QiMen.gGejuDesc[i][0]);
	    }
	    //13. 后天八卦
	    map = xml.readFromFile("后天八卦.xml");
	    if(map.size()>1)
	    for(int i=1; i<=9; i++) {
	    	QiMen.JIUGONGINFO[i][1] = map.get(QiMen.JIUGONGINFO[i][0])==null?"":map.get(QiMen.JIUGONGINFO[i][0]);
	    }
	    //14. 十二神将
	    map = xml.readFromFile("十二神将.xml");
	    if(map.size()>1)
	    for(int i=1; i<=12; i++) {
	    	QiMen2.SHENJ[i][1] = map.get(QiMen2.SHENJ[i][0])==null?"":map.get(QiMen2.SHENJ[i][0]);
	    }
	    //15. 九星值时克应
	    map = xml.readFromFile("九星值时克应.xml");
	    if(map.size()>1)
	    for(int i=1; i<=9; i++) {
				for(int j=1; j<=12; j++) {
					String key = "天"+QiMen.jx1[i]+"值"+YiJing.DIZINAME[j]+"时";
					QiMen2.JXZS[i][j] = map.get(key)==null?"":map.get(key);					
				}				
			}
	    //16. 吉凶格
	    map = xml.readFromFile("吉凶格.xml");
	    if(map.size()>1)
	    for(int i=1; i<=33; i++) {
	    	QiMen.gGejuDesc[i][1] = map.get(QiMen.gGejuDesc[i][0])==null?"":map.get(QiMen.gGejuDesc[i][0]);
	    }
    }catch(Exception e) {
    	e.printStackTrace();
    }
  }

//	public static void test() throws Exception {
//		printJxzs();
//		printInner2XmlFile("十干克应",50,138);
//		printInner2XmlFile("八门克应",170,247);
//		printInner2XmlFile("门干克应",250,328);
//		printInner2XmlFile("星门克应",330,417);
//		printInner2XmlFile("三诈五鬼九遁",420,436);
//		printInner2XmlFile("神门克应",470,533);
//		printInner2XmlFile(QiMen.JIUGONGINFO,"后天八卦",1,9);
//		printInner2XmlFile(QiMen2.SHENJ,"十二神将",1,12);
//		printInner2XmlFile(QiMen2.GAN,"天干",1,10);
//		printInner2XmlFile(QiMen2.ZI,"地支",1,12);
//		printInner2XmlFile(QiMen2.MEN,"八门",1,9);
//		printInner2XmlFile(QiMen2.XING,"九星",1,9);
//		printInner2XmlFile(QiMen2.GUA,"九宫",1,9);
//		printInner2XmlFile(QiMen2.SHEN,"八神",1,8);
//		printInner2XmlFile("吉凶格",1,33);
//}
	
	public static void main(String[] args) throws Exception {
		XmlProc xml = new XmlProc();

	}
}
