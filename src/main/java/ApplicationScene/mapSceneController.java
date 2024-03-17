package ApplicationScene;

import Logic.Client;
import Logic.Database_CL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.MapComponentInitializedListener;
import com.dlsc.gmapsfx.javascript.event.UIEventHandler;
import com.dlsc.gmapsfx.javascript.object.*;

import static com.dlsc.gmapsfx.javascript.event.UIEventType.click;
import static java.lang.Double.parseDouble;

public class mapSceneController extends SwitchScene implements Initializable,
        MapComponentInitializedListener {
        @FXML
        protected GoogleMapView mapView;
        private GoogleMap map;
        @FXML
        private Label userNameLabel;

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            Client client = SceneController.getCurrent_client();
            setUserName(client.getName(), client.getSurname());
            mapView.addMapInitializedListener(this);
        }

    public void setUserName(String userName, String userSurname){
        userNameLabel.setText(userName + " " + userSurname);
    }
        @Override
        public void mapInitialized() {
            MapOptions options = new MapOptions();
            options
                    .center(new LatLong(52.069372870063866, 19.48031990651607))
                    .mapType(MapTypeIdEnum.ROADMAP)
                    .zoom(5);
            mapView.setKey("AIzaSyCOYWdOOddwtV9n835e7J-M1G6n4rFZyJE");
            map = mapView.createMap(options);

            ArrayList<String[]> banks = Database_CL.getBanks();
            for (String[] place : banks){
                LatLong bank = new LatLong(parseDouble(place[0]), parseDouble(place[1]));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(bank);
                Marker New_Bank = new Marker(markerOptions);
                map.addMarker(New_Bank);
                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                infoWindowOptions.content("<h2>"+ place[2]+"</h2>"
                        + place[3]+"<br>"
                        + place[4]+"<br>"
                        + place[5]);
                InfoWindow BankInfoWindow = new InfoWindow(infoWindowOptions);
                UIEventHandler h = jsObject -> BankInfoWindow.open(map, New_Bank);
                map.addUIEventHandler(New_Bank, click, h);
            }
        }
}
