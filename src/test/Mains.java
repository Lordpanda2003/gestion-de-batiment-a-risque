package test;
//Import all the classes and dependencies we are about to use
import data.RoutingData;
import data.RoutingService;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import waypoint.EventWaypoint;
import waypoint.MyWaypoint;
import waypoint.WaypointRender;

public class Mains extends javax.swing.JFrame {

    private final Set<MyWaypoint> waypoints = new HashSet<>();
    private List<RoutingData> routingData = new ArrayList<>();
    private EventWaypoint event;
    private Point mousePosition;

    public Mains() {
        initComponents();
        init();
    }

    //METHODE INIT QUI VA INITIALISER LA CARTE ET SONT CONTENU
    private void init() {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
        //ON DEFINIT LA POSITION DE DEPART ET LE ZOOM DE DEPART
        GeoPosition geo = new GeoPosition(3.502904, 11.293156); //LATITUDE ET LONGITUDE DE YAOUNDE RESPECTIVEMENT
        jXMapViewer.setAddressLocation(geo);
        jXMapViewer.setZoom(10);

        //  Create event mouse move
        MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(mm);
        jXMapViewer.addMouseMotionListener(mm);
        jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
        event = getEvent();
        
        // Define a list of Points for multiple waypoints
        //Collect Geoposition from Database THIS IS A BASIC EXAMPLE
        List<Point> mapPoints = new ArrayList<>();
        mapPoints.add(new Point(577, 322)); // Example position 1
        mapPoints.add(new Point(588, 333)); // Example position 2
        mapPoints.add(new Point(599, 322)); // Example position 3
        
        
        //GRACE A LA METHODE addWayPoint on ajoute les batiment sur la carte EN CONVERTISSANT LES POINTS EN GEOPOSITION
        for (Point points: mapPoints){
            GeoPosition geoPosition = jXMapViewer.convertPointToGeoPosition(points);
            
            // Create a waypoint at the converted GeoPosition
            MyWaypoint wayPoint = new MyWaypoint("Start Location", MyWaypoint.PointType.START, event, geoPosition);
            addWaypoint(wayPoint);
        }
        
    }
    //LA METHODE AJOUTE UN POINT A LA CARTE(JXMAP)
    private void addWaypoint(MyWaypoint waypoint) {
        for (MyWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
        }
        Iterator<MyWaypoint> iter = waypoints.iterator();
        while (iter.hasNext()) {
            if (iter.next().getPointType() == waypoint.getPointType()) {
               // iter.remove();
            }
        }
        waypoints.add(waypoint);
        initWaypoint();
    }
    
    //THIS METHODE INITIALISES THE WAYPOINTS AND STORES THEM IN AN ARRAYLIST TO DEPLAY THEM AS BUTTONS ON THE JXMAP
    private void initWaypoint() {
        WaypointPainter<MyWaypoint> wp = new WaypointRender();
        wp.setWaypoints(waypoints);
        jXMapViewer.setOverlayPainter(wp);
        for (MyWaypoint d : waypoints) {
            jXMapViewer.add(d.getButton());
        }
        //  Routing Data
       /* if (waypoints.size() == 2) {
            GeoPosition start = null;
            GeoPosition end = null;
            for (MyWaypoint w : waypoints) {
                if (w.getPointType() == MyWaypoint.PointType.START) {
                    start = w.getPosition();
                } else if (w.getPointType() == MyWaypoint.PointType.END) {
                    end = w.getPosition();
                }
            }
            if (start != null && end != null) {
                routingData = RoutingService.getInstance().routing(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude());

            } else {
                routingData.clear();
            }
            jXMapViewer.setRoutingData(routingData);
        }*/
    }   
    //THIS METHODE DELETES A WAYPOINT
    private void clearWaypoint() {
        for (MyWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
        }
        routingData.clear();
        waypoints.clear();
        initWaypoint();
    }

    
    //THIS METHODE SHOW DETAILS ON BUILDING
    private EventWaypoint getEvent() {
        
        //We add call the information of the building after clicking on it
        return new EventWaypoint() {
            @Override
            public void selected(MyWaypoint waypoint) {
                //On crée la fenetre du batiment pour affichée ses characteristique en fonction de BD
                BatiInfo batiinfo= new BatiInfo();
                batiinfo.setVisible(true);
            }
        };
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        menuStart = new javax.swing.JMenuItem();
        menuEnd = new javax.swing.JMenuItem();
        jXMapViewer = new data.JXMapViewerCustom();
        comboMapType = new javax.swing.JComboBox<>();

        menuStart.setText("Start");
        menuStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuStartActionPerformed(evt);
            }
        });
        jPopupMenu1.add(menuStart);

        menuEnd.setText("End");
        menuEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEndActionPerformed(evt);
            }
        });
        jPopupMenu1.add(menuEnd);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jXMapViewer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXMapViewerMouseReleased(evt);
            }
        });

        comboMapType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Open Stree", "Virtual Earth", "Hybrid", "Satellite" }));
        comboMapType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMapTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXMapViewerLayout = new javax.swing.GroupLayout(jXMapViewer);
        jXMapViewer.setLayout(jXMapViewerLayout);
        jXMapViewerLayout.setHorizontalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXMapViewerLayout.createSequentialGroup()
                .addContainerGap(990, Short.MAX_VALUE)
                .addComponent(comboMapType, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jXMapViewerLayout.setVerticalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXMapViewerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboMapType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(659, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXMapViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXMapViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboMapTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMapTypeActionPerformed
      //THIS METHODE ENABLES US TO SELECT A VIEW FROM THE MAP
        TileFactoryInfo info;
        int index = comboMapType.getSelectedIndex();
        if (index == 0) {
            info = new OSMTileFactoryInfo();
        } else if (index == 1) {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        } else if (index == 2) {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
        } else {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        }
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
    }//GEN-LAST:event_comboMapTypeActionPerformed

    private void menuStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuStartActionPerformed
        /*GeoPosition geop = jXMapViewer.convertPointToGeoPosition(mousePosition);
        MyWaypoint wayPoint = new MyWaypoint("Start Location", MyWaypoint.PointType.START, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()));
        addWaypoint(wayPoint);*/
    }//GEN-LAST:event_menuStartActionPerformed

    private void menuEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEndActionPerformed
       /* GeoPosition geop = jXMapViewer.convertPointToGeoPosition(mousePosition);
        MyWaypoint wayPoint = new MyWaypoint("End Location", MyWaypoint.PointType.END, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()));
        addWaypoint(wayPoint);*/
    }//GEN-LAST:event_menuEndActionPerformed

    private void jXMapViewerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXMapViewerMouseReleased
       /* if (SwingUtilities.isRightMouseButton(evt)) {
            mousePosition = evt.getPoint();
            jPopupMenu1.show(jXMapViewer, evt.getX(), evt.getY());
        }*/
    }//GEN-LAST:event_jXMapViewerMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Mains.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mains.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mains.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mains.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mains().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboMapType;
    private javax.swing.JPopupMenu jPopupMenu1;
    public data.JXMapViewerCustom jXMapViewer;
    private javax.swing.JMenuItem menuEnd;
    private javax.swing.JMenuItem menuStart;
    // End of variables declaration//GEN-END:variables
}
