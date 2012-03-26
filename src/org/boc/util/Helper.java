package org.boc.util;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Helper {
  public static String SIGN = "_";
  public Helper() {
  }

  public DefaultTreeModel parse(String filename) {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    XMLTreeHandler handler = new XMLTreeHandler();
    File file = new File(filename);
    DefaultMutableTreeNode rootNode;
    DefaultTreeModel theroot;
    try {
    	if(!file.exists()) {//如果没有资源的xml文件，直接创建一个根目录并添加Public.NOW子节点后返回，到时保存时自然有了    		
    		String value = filename.substring(filename.lastIndexOf("/")+1,filename.lastIndexOf(".xml"));
    		theroot =  new DefaultTreeModel(new DefaultMutableTreeNode(Public.mapRootValueKey.get(value)));
    		rootNode = (DefaultMutableTreeNode)theroot.getRoot();
    		rootNode.add(new DefaultMutableTreeNode(Public.NOW));
    		return new DefaultTreeModel(rootNode);
    	}
      // Parse the input.
      SAXParser saxParser = factory.newSAXParser();
      saxParser.parse(file, handler);
      rootNode = handler.getRoot();
      //如果没有Public.NOW节点，则必须加进去
      boolean hasNow = false;
      for(int i=0; i<rootNode.getChildCount(); i++) {
      	if(rootNode.getChildAt(i).toString().equals(Public.NOW)) {
      		hasNow = true;
      		break;
      	}
      }
      if(!hasNow) rootNode.add(new DefaultMutableTreeNode(Public.NOW));
      theroot = new DefaultTreeModel(rootNode);
    }
    catch (Exception e) {
    	e.printStackTrace();
      Messages.error("解析xml文件出错："+e);
      return new DefaultTreeModel(new DefaultMutableTreeNode("error"));
    }
    return theroot;
  }

  public static class XMLTreeHandler
      extends DefaultHandler {
    private DefaultMutableTreeNode root, currentNode;
    public DefaultMutableTreeNode getRoot() {
      return root;
    }

    // SAX parser handler methods
    public void startElement(String namespaceURI, String lName, String qName,
                             Attributes attrs) throws SAXException {
      String eName = lName; // Element name
      if ("".equals(eName)) {
        int i = qName.indexOf(SIGN);
        if(i!=-1) {
          eName = qName.substring(0,i);
        }else{
          eName = qName;
        }
      }
      Tag t = new Tag(eName, attrs);
      DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(t);
      if (currentNode == null) {
        root = newNode;
      }
      else {
        // Must not be the root node
        currentNode.add(newNode);
      }
      currentNode = newNode;
    }

    public void endElement(String namespaceURI, String sName, String qName) throws
        SAXException {
      currentNode = (DefaultMutableTreeNode) currentNode.getParent();
    }

    public void characters(char buf[], int offset, int len) throws SAXException {
      String s = new String(buf, offset, len).trim();
      ( (Tag) currentNode.getUserObject()).addData(s);
    }
  }

  public static class Tag {
    private String name;
    private String data;
    private Attributes attr;

    public Tag(String n, Attributes a) {
      name = n;
      attr = a;
    }

    public String getName() {
      int i = name.indexOf(SIGN);
      if(i!=-1) {
        return name.substring(0,i);
      }

      return name;
    }

    public Attributes getAttributes() {
      return attr;
    }

    public void setData(String d) {
      data = d;
    }

    public String getData() {
      return data;
    }

    public void addData(String d) {
      if (data == null) {
        setData(d);
      }
      else {
        data += d;
      }
    }

    public String getAttributesAsString() {
      StringBuffer buf = new StringBuffer(256);
      for (int i = 0; i < attr.getLength(); i++) {
        buf.append(attr.getQName(i));
        buf.append("=\"");
        buf.append(attr.getValue(i));
        buf.append("\"");
      }
      return buf.toString();
    }

    public String toString() {
      return getName();
    }
  }

}
