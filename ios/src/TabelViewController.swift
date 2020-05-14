//
//  TabelViewController.swift
//  FYEApp
//
//  Created by Ibrahim Roshdy on 7/14/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import UIKit
import Firebase

class TabelViewController: UIViewController,UITableViewDataSource,UITableViewDelegate {

    
    //let food:[String] = ["item1","item2","item3","item4","item5"]
    var arrayOfNames:NSArray = []
    var arrayOfnames:[String] = []
    var arrayOfType:[String] = []
    var arrayOfImages:[String] = []
    var hasImage:Bool = false
    var arrayOfInfo:[String] = []
    var arrayOfLinks:[String] = []
    var isMajor:Bool = false
    var isLinks:Bool = false
    var isMessaging:Bool = false
    var needsScroll:Bool = false
    var FYE:Bool = false
    var isCompetition:Bool = false
    var isYoutube:Bool = false
    var number:Int = 0
    override func viewDidLoad() {
        super.viewDidLoad()
        

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if ((!isMajor)&&(!isMessaging)&&(!FYE)){
        return arrayOfNames.count
        }
        else{
        return arrayOfnames.count
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! TableViewCell // create cell like tableviewcell
    
        if ((!isMajor)&&(!isMessaging)&&(!FYE)){
        cell.mylabel.text = arrayOfNames[indexPath.row] as? String// assign label
        }
        else
        {
            print(arrayOfnames)
            cell.mylabel.text = arrayOfnames[indexPath.row]
        }
        
        if(hasImage){
            let url_image = NSURL(string: "http://188.226.144.157/group1/images/" + arrayOfImages[indexPath.row])
            let data_img = NSData(contentsOf: url_image! as URL)
           
            cell.myimage.image = UIImage (data: data_img! as Data) // assign image
        }
        cell.contentView.layer.removeAllAnimations()

        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if(segue.identifier == "openResults"){
            let destVC: CompetitionVC=segue.destination as! CompetitionVC
            //sends array of names and images to table
            destVC.house = String(number+1)
            destVC.info = arrayOfInfo[number]
        } else
        if(segue.identifier == "openYoutube"){
            let destVC: YoutubeVC=segue.destination as! YoutubeVC
            //sends array of names and images to table
            destVC.code = arrayOfInfo[number]
        }
        else if (segue.identifier == "FYEVCToSettingsVC"){
            let destVC: SettingsVC=segue.destination as! SettingsVC
        }
    }
    

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        if (FYE)
        {
            if (isCompetition)
            {
                 number = indexPath.row
                 self.performSegue(withIdentifier: "openResults", sender: nil)
            }
            if (isYoutube)
            {
                 self.performSegue(withIdentifier: "openYoutube", sender: nil)
            }
        }
        else
        if (isMajor)
        {
            print(self.arrayOfLinks)
            let alertVC = UIAlertController(title: arrayOfnames[indexPath.row] as! String, message: arrayOfInfo[indexPath.row], preferredStyle: .alert)
            if (arrayOfType[indexPath.row] != "Minor")
            {
                let alertActionOkay = UIAlertAction(title: "More Info", style: .default) {
                    (_) in
                    
                    let url = URL(string: self.arrayOfLinks[indexPath.row])!
                    if #available(iOS 10.0, *) {
                        UIApplication.shared.open(url, options: [:], completionHandler: nil)
                    } else {
                        UIApplication.shared.openURL(url)
                    }
                }
                alertVC.addAction(alertActionOkay)
            }
           
            let alertActionCancel = UIAlertAction(title: "Done", style: .default, handler: nil)
            
           
            alertVC.addAction(alertActionCancel)
            self.present(alertVC, animated: true, completion: nil)
        }
        else
        if (isMessaging)
        {
            switch(indexPath.row)
            {
            case 0:
                if (arrayOfnames.contains("Announcements"))
                {
                 var channelRef: DatabaseReference = Database.database().reference().child("Announcements")
                     var channelRefHandle: DatabaseHandle?
                }
                else {
                var channelRef: DatabaseReference = Database.database().reference().child("House "+arrayOfnames[0])
                var channelRefHandle: DatabaseHandle?
                }
            case 1:
                var channelRef: DatabaseReference = Database.database().reference().child("Group "+arrayOfnames[1])
                var channelRefHandle: DatabaseHandle?
            case 2:
                var channelRef: DatabaseReference = Database.database().reference().child("Announcements")
                var channelRefHandle: DatabaseHandle?
            default:
                print("no")
            }
            self.performSegue(withIdentifier: "openMessages", sender: nil)
                    }
        else
        if (isLinks)
        {
            let alertVC = UIAlertController(title: arrayOfNames[indexPath.row] as! String, message: arrayOfLinks[indexPath.row], preferredStyle: .alert)
            let alertActionOkay = UIAlertAction(title: "Open Link", style: .default) {
                (_) in
                let url = URL(string: self.arrayOfLinks[indexPath.row])!
                if #available(iOS 10.0, *) {
                    UIApplication.shared.open(url, options: [:], completionHandler: nil)
                } else {
                    UIApplication.shared.openURL(url)
                }
            }
            let alertActionCancel = UIAlertAction(title: "Done", style: .default, handler: nil)
            
            alertVC.addAction(alertActionOkay)
            alertVC.addAction(alertActionCancel)
            self.present(alertVC, animated: true, completion: nil)

        }
        else
        if (needsScroll)
        {
            let popUpVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "popUpID") as! PopUpVC
            
            self.addChild(popUpVC)
            popUpVC.view.frame = self.view.frame
            self.view.addSubview(popUpVC.view)
            popUpVC.didMove(toParent: self)
            
            popUpVC.popUpLabel.text = arrayOfInfo[indexPath.row]
            print(popUpVC.info)
        }
        else
        {
            let alertVC = UIAlertController(title: arrayOfNames[indexPath.row] as! String, message: arrayOfInfo[indexPath.row], preferredStyle: .alert)
            let alertActionCancel = UIAlertAction(title: "Done", style: .default, handler: nil)
            
            alertVC.addAction(alertActionCancel)
            self.present(alertVC, animated: true, completion: nil)
            
        }
   
        
      //  let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! TableViewCell // create cell like tableviewcell
        
        //cell.effect = UIBlurEffect.init(style: UIBlurEffectStyle(rawValue: Int(B0))!)
        

        
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
