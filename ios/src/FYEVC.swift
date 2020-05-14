//
//  FYEVC.swift
//  FYEApp
//
//  Created by Ibrahim Roshdy on 7/13/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import UIKit

class FYEVC: UIViewController {
    
    var picurl:String = ""
    var hasImage:Bool = false
    var isLinks:Bool = false
    var isMajor:Bool = false
    var needsScroll:Bool = false
    var isMessaging:Bool = false
    var isCompetition:Bool = false
    var isYoutube:Bool = false
    var online:Bool = false
    var arrayOfnames:[String] = []
    var arrayOfInfo:[String] = []
    var Videos:NSArray = []
    var dictionaryOfData:NSDictionary = [:]
    
    //missing settings and all buttons
    @IBOutlet weak var scheduleBtn: UIButton!
    @IBOutlet weak var guideBtn: UIButton!
    @IBOutlet weak var competitionBtn: UIButton!
    @IBOutlet weak var youtubeBtn: UIButton!
    @IBOutlet weak var majorfairBtn: UIButton!
    @IBOutlet weak var engagementfairBtn: UIButton!
    @IBOutlet weak var treasurehuntBtn: UIButton!
    @IBOutlet weak var stationsBtn: UIButton!
    
    @IBAction func helpBtnPressed(_ sender: Any) {
                self.performSegue(withIdentifier: "FYEVCToHelpVC", sender: nil)
    }
    @IBAction func settingsBtnPressed(_ sender: Any) {
        self.performSegue(withIdentifier: "FYEVCToSettingsVC", sender: nil)
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

    @IBAction func stationsBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            picurl = "http://188.226.144.157/group1/images/FYEStations.png"
            self.performSegue(withIdentifier: "openPicture", sender: nil)
        }
    }

    @IBAction func engagementfairBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            picurl = "http://188.226.144.157/group1/images/EngagementFair.png"
            self.performSegue(withIdentifier: "openPicture", sender: nil)
        }
    }
 
    @IBAction func majorsfairBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            picurl = "http://188.226.144.157/group1/images/MajorsFair.png"
            self.performSegue(withIdentifier: "openPicture", sender: nil)
        }
    }

    @IBAction func youtubeBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            
            arrayOfnames.removeAll()
            arrayOfInfo.removeAll()
            isYoutube = true;
            isCompetition = false;
            let url1 = NSURL(string: "http://188.226.144.157/group1/ios/Fetch_Videos_ios.php")
            let dataOfURL1 = NSData(contentsOf: url1! as URL)
            
            Videos = try! JSONSerialization.jsonObject(with: dataOfURL1! as Data, options: JSONSerialization.ReadingOptions.mutableContainers) as! NSArray
            
            for i in 0..<Videos.count{
                dictionaryOfData = Videos[i] as! NSDictionary
                arrayOfnames.append(dictionaryOfData["Name"] as! String)
                arrayOfInfo.append(dictionaryOfData["URL"] as! String)
            }
            get()
        }

    }

    @IBAction func competitionBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            arrayOfnames.removeAll()
            arrayOfInfo.removeAll()
            isCompetition = true
            isYoutube = false
            let url1 = NSURL(string: "http://188.226.144.157/group1/ios/Fetch_Competition_ios.php")
            let dataOfURL1 = NSData(contentsOf: url1! as URL)
            
            Videos = try! JSONSerialization.jsonObject(with: dataOfURL1! as Data, options: JSONSerialization.ReadingOptions.mutableContainers) as! NSArray
            
            for i in 0..<Videos.count{
                dictionaryOfData = Videos[i] as! NSDictionary
                arrayOfnames.append("House "+(dictionaryOfData["House"] as! String))
                let first = (dictionaryOfData["First"] as! String)
                let second = ","+(dictionaryOfData["Second"] as! String)
                let third = ","+(dictionaryOfData["Third"] as! String)
                let ranking = ","+(dictionaryOfData["Ranking"] as! String)
                arrayOfInfo.append(first+second+third+ranking)
            }
            get()
        }
    }

    @IBAction func guideBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            picurl = "http://188.226.144.157/group1/images/Guide.png"
            self.performSegue(withIdentifier: "openPicture", sender: nil)
        }
    }

    
    @IBAction func scheduleBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            picurl = "http://188.226.144.157/group1/images/Schedule.png"
            self.performSegue(withIdentifier: "openPicture", sender: nil)
        }
    }
    
    @IBAction func treasureBtnPressed(_ sender: Any) {
        NotificationCenter.default.addObserver(self, selector: #selector(statusManager), name: .flagsChanged, object: Network.reachability)
        updateUserInterface()
        if (online) {
            picurl = "http://188.226.144.157/group1/images/TreasureHunt.png"
            self.performSegue(withIdentifier: "openPicture", sender: nil)
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
    
    func get()
    {
        self.performSegue(withIdentifier: "segueToTableView", sender: self)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if(segue.identifier == "segueToTableView"){
            let destVC: TabelViewController=segue.destination as! TabelViewController
            //sends array of names and images to table
            destVC.arrayOfnames = arrayOfnames
            destVC.arrayOfInfo = arrayOfInfo
            destVC.FYE = true
            destVC.isCompetition = isCompetition
            destVC.isYoutube = isYoutube
        }else if (segue.identifier == "FYEVCToSettingsVC"){
            let destVC: SettingsVC=segue.destination as! SettingsVC
        }
        else if (segue.identifier == "openPicture") {
            let destVC: PictureVC=segue.destination as! PictureVC
            destVC.picurl = picurl
        }
        else if (segue.identifier == "FYEVCToHelpVC") {
            let destVC: HelpVC=segue.destination as! HelpVC
        }
        
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
