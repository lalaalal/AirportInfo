package com.airportinfo.view;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.airportinfo.Airport;
import com.airportinfo.utils.DBManager;

import javax.imageio.ImageIO;

class SubFrame extends JFrame {
	
	String region = null;
	ArrayList<Airport> airportList;
	
	Object rows[][];
	String columns[] = { "EnglishName", "KoreanName", "IATA", "ICAO", 
			"Region", "EnglishCountryName", "KoreanCountryName", "EnglishCityName" };
	TableModel model;
	    final JTable table;
	    final TableRowSorter<TableModel> sorter;
	    JScrollPane pane;
	    JPanel panel;
	    JLabel label = new JLabel("Filter");
	    final JTextField filterText;
	    JButton button = new JButton("Filter");

    public SubFrame(String region, ArrayList<Airport> airports){
        super("SubFrame");
        
        this.region = region;
        this.airportList = airports;
        int i = 0;
        this.rows = new Object[airports.size()][8];
        for(Airport item : airports) {
    		rows[i][0] = item.englishName;
    		rows[i][1] = item.koreanName;
    		rows[i][2] = item.IATA;
    		rows[i][3] = item.ICAO;
    		rows[i][4] = item.region;
    		rows[i][5] = item.englishCountryName;
    		rows[i][6] = item.koreanCountryName;
    		rows[i][7] = item.englishCityName;
    		i++;
    	}
        
        model = new DefaultTableModel(rows, columns) {
  	      public Class getColumnClass(int column) {
  	        Class returnValue;
  	        if ((column >= 0) && (column < getColumnCount())) {
  	          returnValue = getValueAt(0, column).getClass();
  	        } else {
  	          returnValue = Object.class;
  	        }
  	        return returnValue;
  	      }
  	    };    
  	    table = new JTable(model);
  	    sorter = new TableRowSorter<TableModel>(model);
  	    pane = new JScrollPane(table);
  	    panel = new JPanel(new BorderLayout());
  	    filterText = new JTextField("���⿡ �˻�...");
        
        setSize(100,100);
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		setVisible(false);
        		dispose();
        	}
        });
        setLocation(200, 0);
        setVisible(true);
        
        table.setRowSorter(sorter);
	    add(pane, BorderLayout.CENTER);
	    panel.add(label, BorderLayout.WEST);
	    panel.add(filterText, BorderLayout.CENTER);
	    add(panel, BorderLayout.NORTH);
        add(button, BorderLayout.SOUTH);
        setSize(1300, 700);
        
        button.addActionListener(new ActionListener() {
  	      public void actionPerformed(ActionEvent e) {
  	        String text = filterText.getText();
  	        if (text.length() == 0) {
  	          sorter.setRowFilter(null);
  	        } else {
  	          sorter.setRowFilter(RowFilter.regexFilter(text));
  	        }
  	      }
  	    });
    }
}

public class WorldMapArea {

    private JComponent ui = null;
    JLabel output = new JLabel();
    public static final int SIZE = 750;
    BufferedImage image;
    Area area;
    ArrayList<Shape> shapeList;
    
    HashMap<Rectangle, String> regionHashMap = new HashMap<Rectangle, String>();
    
    DBManager dm;

    public WorldMapArea() {
        try {
        	initHashMap();
            initUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void initHashMap() {
    	Rectangle Asia = new Rectangle(851,72,629,375);
        Rectangle Europe = new Rectangle(662,126,206,199);
        Rectangle LatinAmerica = new Rectangle(218,341,336,395);
        Rectangle Africa = new Rectangle(629,322,280,256);
        Rectangle MiddleEast = new Rectangle(818,305,126,125);
        Rectangle NorthAfrica = new Rectangle(10,120,458,255);
        Rectangle SouthAfrica = new Rectangle(756,548,88,81);
        Rectangle Oceania = new Rectangle(1167,529,163,120);
    	regionHashMap.put(Asia, "�ƽþ�");
    	regionHashMap.put(Europe, "����");
    	regionHashMap.put(LatinAmerica, "�߳���");
    	regionHashMap.put(Africa, "������ī");
    	regionHashMap.put(MiddleEast, "�ߵ�");
    	regionHashMap.put(NorthAfrica, "�Ϲ�");
    	regionHashMap.put(SouthAfrica, "����");
    	regionHashMap.put(Oceania, "�����");
    }

    public final void initUI() throws Exception {
        if (ui != null) {
            return;
        }
        URL url = new URL("https://cdn.discordapp.com/attachments/978261181896728647/1035439568121499678/N4eOn_-_.png");
        image = ImageIO.read(url);
        long then = System.currentTimeMillis();
        System.out.println("" + then);
        area = getOutline(Color.WHITE, image, 12);
        long now = System.currentTimeMillis();
        System.out.println("Time in mins: " + (now - then) / 60000d);
        shapeList = separateShapeIntoRegions(area);
        ui = new JPanel(new BorderLayout(4, 4));
        ui.setBorder(new EmptyBorder(4, 4, 4, 4));

        output.addMouseMotionListener(new MousePositionListener());
        output.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(isInLand()==true) {
					try {
						dm = new DBManager();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						String rn = getRegionName();
						System.out.println(rn);
						SubFrame sf = new SubFrame(rn, dm.getAirportList(rn));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

        ui.add(output);

        refresh();
    }
    public boolean isInLand() {
    	if(getShape()!=null) {
    		return true;
    	}
    	return false;
    }
    
    public String getRegionName() {
    	Rectangle r = new Rectangle();
    	r = getShape();
    	System.out.println(r.getBounds());
    	return r!=null ? regionHashMap.get(r) : null;
    }

    public Area getOutline(Color target, BufferedImage bi, int tolerance) {
        GeneralPath gp = new GeneralPath();

        boolean cont = false;
        for (int xx = 0; xx < bi.getWidth(); xx++) {
            for (int yy = 0; yy < bi.getHeight(); yy++) {
                if (isIncluded(new Color(bi.getRGB(xx, yy)), target, tolerance)) {
                    //if (bi.getRGB(xx,yy)==targetRGB) {
                    if (cont) {
                        gp.lineTo(xx, yy);
                        gp.lineTo(xx, yy + 1);
                        gp.lineTo(xx + 1, yy + 1);
                        gp.lineTo(xx + 1, yy);
                        gp.lineTo(xx, yy);
                    } else {
                        gp.moveTo(xx, yy);
                    }
                    cont = true;
                } else {
                    cont = false;
                }
            }
            cont = false;
        }
        gp.closePath();

        return new Area(gp);
    }

    public static ArrayList<Shape> separateShapeIntoRegions(Shape shape) {
        ArrayList<Shape> regions = new ArrayList<>();

        PathIterator pi = shape.getPathIterator(null);
        GeneralPath gp = new GeneralPath();
        while (!pi.isDone()) {
            double[] coords = new double[6];
            int pathSegmentType = pi.currentSegment(coords);
            int windingRule = pi.getWindingRule();
            gp.setWindingRule(windingRule);
            if (pathSegmentType == PathIterator.SEG_MOVETO) {
                gp = new GeneralPath();
                gp.setWindingRule(windingRule);
                gp.moveTo(coords[0], coords[1]);
            } else if (pathSegmentType == PathIterator.SEG_LINETO) {
                gp.lineTo(coords[0], coords[1]);
            } else if (pathSegmentType == PathIterator.SEG_QUADTO) {
                gp.quadTo(coords[0], coords[1], coords[2], coords[3]);
            } else if (pathSegmentType == PathIterator.SEG_CUBICTO) {
                gp.curveTo(
                        coords[0], coords[1],
                        coords[2], coords[3],
                        coords[4], coords[5]);
            } else if (pathSegmentType == PathIterator.SEG_CLOSE) {
                gp.closePath();
                regions.add(new Area(gp));
            } else {
                System.err.println("Unexpected value! " + pathSegmentType);
            }

            pi.next();
        }

        return regions;
    }

    class MousePositionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            // XXXXXXX
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            refresh();
        }
    }

    public static boolean isIncluded(Color target, Color pixel, int tolerance) {
        int rT = target.getRed();
        int gT = target.getGreen();
        int bT = target.getBlue();
        int rP = pixel.getRed();
        int gP = pixel.getGreen();
        int bP = pixel.getBlue();
        return ((rP - tolerance <= rT) && (rT <= rP + tolerance)
                && (gP - tolerance <= gT) && (gT <= gP + tolerance)
                && (bP - tolerance <= bT) && (bT <= bP + tolerance));
    }

    private void refresh() {
        output.setIcon(new ImageIcon(getImage()));
    }
    
    private Rectangle getShape() {

        try {
            Point p = MouseInfo.getPointerInfo().getLocation();
            Point p1 = output.getLocationOnScreen();
            int x = p.x - p1.x;
            int y = p.y - p1.y;
            Point pointOnImage = new Point(x, y);
            for (Shape shape : shapeList) {
                if (shape.contains(pointOnImage)) {
                    return shape.getBounds();
                }
            }
        } catch (Exception doNothing) {
        }
		return null;
    }

    private BufferedImage getImage() {
        BufferedImage bi = new BufferedImage(
                2 * SIZE, SIZE, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bi.createGraphics();
        g.drawImage(image, 0, 0, output);
        g.setColor(Color.WHITE);
        g.fill(area);
        g.setColor(Color.GRAY.brighter());
        g.draw(area);
        try {
            Point p = MouseInfo.getPointerInfo().getLocation();
            Point p1 = output.getLocationOnScreen();
            int x = p.x - p1.x;
            int y = p.y - p1.y;
            Point pointOnImage = new Point(x, y);
            for (Shape shape : shapeList) {
                if (shape.contains(pointOnImage)) {
                    g.setColor(Color.GREEN.darker());
                    g.fill(shape);
                    break;
                }
            }
        } catch (Exception doNothing) {
        }

        g.dispose();

        return bi;
    }

    public JComponent getUI() {
        return ui;
    }

    public static void main(String[] args) {
        Runnable r = () -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            WorldMapArea o = new WorldMapArea();
           

            JFrame f = new JFrame(o.getClass().getSimpleName());
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationByPlatform(true);

            f.setContentPane(o.getUI());
            f.setResizable(false);
            f.pack();

            f.setVisible(true);
            
        };
        SwingUtilities.invokeLater(r);
    }
}