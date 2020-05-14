//
//  AUCVC.swift
//  FYEApp
//
//  Created by Ibrahim Roshdy on 7/13/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import UIKit
import WebKit

//import Foundation

class AUCVC: UIViewController {
    
    @IBOutlet weak var academicGuideBtn: UIButton!
    @IBOutlet weak var mapsBtn: UIButton!
    @IBOutlet weak var clubsBtn: UIButton!
    @IBOutlet weak var messageBoardBtn: UIButton!
    @IBOutlet weak var faqBtn: UIButton!
    @IBOutlet weak var discountsBtn: UIButton!
    @IBOutlet weak var linksBtn: UIButton!
    @IBOutlet weak var foodBtn: UIButton!
    @IBOutlet weak var facilitiesBtn: UIButton!
    
    @IBOutlet weak var settingsBtn: UIBarButtonItem!
    
    var arrayOfNames:NSArray = []
    var arrayOfnames:[String] = []
    var arrayOfImages:[String] = []
    var arrayOfInfo:[String] = []
    var arrayOfLinks:[String] = []
    var arrayOfType:[String] = []
    var arrayOfData:NSArray = []
    var dictionaryOfData:NSDictionary = [:]
    
    var hasImage:Bool = false
    var isLinks:Bool = false
    var isMajor:Bool = false
    var needsScroll:Bool = false
    var isMessaging:Bool = false
    var online:Bool = false
    
    //We need to add FAQ and majors
    
    @IBAction func helpBtnPressed(_ sender: Any) {
            self.performSegue(withIdentifier: "AUCVCToHelpVC", sender: nil)
    }
    
    @IBAction func settingsBtnPressed(_ sender:Any) {
        self.performSegue(withIdentifier: "AUCVCToSettingsVC", sender: nil)
    }
    
    func updateUserInterface() {
        guard let status = Network.reachability?.status else { return }
        switch status {
        case .unreachable:
            let alertVC = UIAlertController(title: "Connection Error", message: "You need to connect to the internet to access all the features.", preferredStyle: .alert)
            
            let alertActionCancel = UIAlertAction(title: "Okay", style: .default, handler: nil)
            let settingsAction = UIAlertAction(title: "Settings", style: .default) { (_) -> Void in
                guard let settingsUrl = URL(string: UIApplication.openSettingsURLString) else {
                    return
                }
                
                if UIApplication.shared.canOpenURL(settingsUrl) {
                    UIApplication.shared.open(settingsUrl, completionHandler: { (success) in
                        print("Settings opened: \(success)") // Prints true
                    })
                }
            }
            alertVC.addAction(settingsAction)
            alertVC.addAction(alertActionCancel)
            self.present(alertVC, animated: true, completion: nil)
            online = false
        case .wifi:
            online = true
        case .wwan:
            online = true
        }
        print("Reachability Summary")
        print("Status:", status)
        print("HostName:", Network.reachability?.hostname ?? "nil")
        print("Reachable:", Network.reachability?.isReachable ?? "nil")
        print("Wifi:", Network.reachability?.isReachableViaWiFi ?? "nil")
    }
    
    @objc func statusManager(_ notification: NSNotification) {
        updateUserInterface()
    }
    
    @IBAction func majorsBtnPressed(_ sender: Any) {
        
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            let urlData:String = "http://188.226.144.157/group1/ios/find_academic_data_ios.php"
            hasImage = false
            isLinks = false
            isMajor = true;
            needsScroll = true;
            isMessaging = false;
            get(urlName: urlData,urlData: urlData,hasImage: hasImage,isLinks: isLinks, isMajor: isMajor, needsScroll: needsScroll, isMessaging: isMessaging)
        }
    }

    @IBAction func mapsBtnPressed(_ sender: Any) {
          self.performSegue(withIdentifier: "AUCVCToComingSoon", sender: nil)
    }
    
    @IBAction func faqBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            let urlName:String = "http://188.226.144.157/group1/find_allFAQ.php"
            let urlData:String = "http://188.226.144.157/group1/ios/find_faq_data_ios.php"
            hasImage = false
            isLinks = false;
            isMajor = false;
            needsScroll = true;
            isMessaging = false;
            get(urlName: urlName,urlData: urlData,hasImage: hasImage,isLinks: isLinks, isMajor: isMajor, needsScroll: needsScroll, isMessaging: isMessaging)
        }
    }
    
 
    @IBAction func linksBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            let urlName:String = "http://188.226.144.157/group1/find_links_names.php"
            let urlData:String = "http://188.226.144.157/group1/ios/find_links_data_ios.php"
            hasImage = false
            isLinks = true;
            isMajor = false;
            needsScroll = false;
            isMessaging = false;
            
            get(urlName: urlName,urlData: urlData, hasImage: hasImage,isLinks: isLinks, isMajor: isMajor, needsScroll: needsScroll, isMessaging: isMessaging)
        }
    }
    
    @IBAction func messageboardBtnPressed(_ sender: Any) {
        arrayOfnames.removeAll()
        arrayOfnames = []
        arrayOfLinks.removeAll()
        arrayOfLinks = []
        arrayOfType.removeAll()
        arrayOfType = []
        arrayOfImages.removeAll()
        arrayOfImages = []
        arrayOfInfo.removeAll()
        arrayOfInfo = []
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            let userDefaults = UserDefaults.standard
            if (userDefaults.string(forKey: "housedata") != "None") {
                arrayOfnames.append("House "+userDefaults.string(forKey: "housedata")!+" Message Board")
                arrayOfnames.append("Group "+userDefaults.string(forKey: "groupdata")!+" Message Board")
            }
            arrayOfnames.append("Announcements Message Board")
            hasImage = false
            isLinks = false
            isMajor = false;
            needsScroll = false;
            isMessaging = true;
            get(urlName: "",urlData: "",hasImage: hasImage,isLinks: isLinks, isMajor: isMajor, needsScroll: needsScroll, isMessaging: isMessaging)
        }
    }

    @IBAction func clubsBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            let urlName:String = "http://188.226.144.157/group1/find_allclubs.php"
            let urlData:String = "http://188.226.144.157/group1/ios/find_clubs_data_ios.php"
            hasImage = true
            isLinks = false;
            isMajor = false;
            needsScroll = false;
            isMessaging = false;
            get(urlName: urlName,urlData: urlData,hasImage: hasImage,isLinks: isLinks, isMajor: isMajor, needsScroll: needsScroll, isMessaging: isMessaging)
        }
    }

    @IBAction func discountsBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            let urlName:String = "http://188.226.144.157/group1/find_allstores.php"
            let urlData:String = "http://188.226.144.157/group1/ios/find_stores_data_ios.php"
            hasImage = true
            isLinks = false;
            isMajor = false;
            needsScroll = false;
            isMessaging = false;
            get(urlName: urlName,urlData: urlData,hasImage: hasImage,isLinks: isLinks, isMajor: isMajor, needsScroll: needsScroll, isMessaging: isMessaging)
        }
    }
    
    
    @IBAction func foodBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            let urlName:String = "http://188.226.144.157/group1/find_allfood.php"
            let urlData:String = "http://188.226.144.157/group1/ios/find_food_data_ios.php"
            hasImage = true
            isLinks = false;
            isMajor = false;
            needsScroll = false;
            isMessaging = false;
            get(urlName: urlName,urlData: urlData,hasImage: hasImage,isLinks: isLinks, isMajor: isMajor, needsScroll: needsScroll, isMessaging: isMessaging)
        }
    }

    
    @IBAction func facilitiesBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            let urlName:String = "http://188.226.144.157/group1/find_fsnames.php"
            let urlData:String = "http://188.226.144.157/group1/ios/find_facility_data.php" ///NEEDS TO BE CHANGED
            hasImage = true;
            isLinks = false;
            isMajor = false;
            needsScroll = true;
            isMessaging = false;
            get(urlName: urlName,urlData: urlData,hasImage: hasImage,isLinks: isLinks, isMajor: isMajor, needsScroll: needsScroll, isMessaging: isMessaging)
        }

    }
    
    
    func get(urlName :String, urlData:String, hasImage:Bool, isLinks:Bool, isMajor:Bool, needsScroll:Bool, isMessaging:Bool)
    {
        
        if(arrayOfData.count != 0) {
            arrayOfnames.removeAll()
            arrayOfnames = []
            arrayOfLinks.removeAll()
            arrayOfLinks = []
            arrayOfType.removeAll()
            arrayOfType = []
            arrayOfImages.removeAll()
            arrayOfImages = []
            arrayOfInfo.removeAll()
            arrayOfInfo = []
            
        }
        
        if (!isMessaging) {
 
        
        if (!isMajor)
        {
            
        let url = NSURL(string: urlName)
        let dataOfURL = NSData(contentsOf: url! as URL)
        
        arrayOfNames = try! JSONSerialization.jsonObject(with: dataOfURL! as Data, options: JSONSerialization.ReadingOptions.mutableContainers) as! NSArray
        
        }
        
        let url_data = NSURL(string: urlData)
        let dataOfURL_data = NSData(contentsOf: url_data! as URL)
        arrayOfData = try! JSONSerialization.jsonObject(with: dataOfURL_data! as Data, options: JSONSerialization.ReadingOptions.allowFragments) as! NSArray
        
        for i in 0..<arrayOfData.count{
            dictionaryOfData = arrayOfData[i] as! NSDictionary
            if (isMajor)
            {
                arrayOfType.append(dictionaryOfData["Type"]as! String)
                arrayOfnames.append(dictionaryOfData["Name"]as! String)
                arrayOfInfo.append(dictionaryOfData["Info"]as! String)
                arrayOfLinks.append(dictionaryOfData["Link"]as! String)
            }
            else
            if(isLinks){
                
                arrayOfLinks.append(dictionaryOfData["Link"]as! String)
            }else {
                arrayOfInfo.append(dictionaryOfData["Info"] as! String)
            }
            
            if (hasImage) {
            arrayOfImages.append(dictionaryOfData["Logo"] as! String)
            }
        }
        }
        self.performSegue(withIdentifier: "segueToTableView", sender: self)
        
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if(segue.identifier == "segueToTableView"){
            let destVC: TabelViewController=segue.destination as! TabelViewController
            //sends array of names and images to table
            destVC.arrayOfnames = arrayOfnames
            destVC.arrayOfNames = arrayOfNames
            destVC.arrayOfType = arrayOfType
            destVC.arrayOfImages = arrayOfImages
            destVC.arrayOfInfo = arrayOfInfo
            destVC.arrayOfLinks = arrayOfLinks
            destVC.hasImage = hasImage
            destVC.isMajor = isMajor
            destVC.isLinks = isLinks
            destVC.needsScroll = needsScroll
            destVC.isMessaging = isMessaging
        }else if (segue.identifier == "AUCVCToSettingsVC"){
             let destVC: SettingsVC=segue.destination as! SettingsVC
        }
        else if (segue.identifier == "AUCVCToHelpVC"){
            let destVC: HelpVC=segue.destination as! HelpVC
        }
    
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    open override var shouldAutorotate: Bool {
        get {
            return false
        }
    }
    
    override var supportedInterfaceOrientations: UIInterfaceOrientationMask {
        get {
            return .portrait
        }
    }
    
    
}
