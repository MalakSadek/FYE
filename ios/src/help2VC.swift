//
//  helpStartVC.swift
//
//
//  Created by Malak Sadek on 7/20/17.
//
//

import UIKit

class help2VC: UIViewController,UITableViewDataSource,UITableViewDelegate {
    @IBAction func startBtnPressed(_ sender: Any) {
          performSegue(withIdentifier: "HelpVCToTabBar", sender: nil)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if(segue.identifier == "HelpVCToTabBar"){
            let destVC: UITabBarController = segue.destination as! UITabBarController
        }
    }
    @available(iOS 2.0, *)
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! TableViewCell // create cell like tableviewcell
   
            cell.mylabel.text = arrayOfInfo[indexPath.row] // assign label
            cell.myimage.image = arrayOfImages[indexPath.row] // assign image
        
        
        cell.contentView.layer.removeAllAnimations()
        
        return cell
    }
    
    @available(iOS 2.0, *)
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return arrayOfInfo.count
    }
    
    
    var arrayOfInfo:[String] = []
    var arrayOfImages:[UIImage] = []
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        arrayOfInfo.append("AUC Tab Features:")
        arrayOfInfo.append("Academic Guide: Lists all the majors, minors, and the core curriculum.")
        arrayOfInfo.append("Clubs: Lists all the clubs and how to contact them.")
        arrayOfInfo.append("Maps: Coming Soon To iOS.")
        arrayOfInfo.append("Message Boards: Opens a list of message boards you are automatically subscribed to.")
        arrayOfInfo.append("FAQ: Opens a list of frequently asked questions by freshmen and their answers.")
        arrayOfInfo.append("Links: Opens a list of all the useful links for an AUC student.")
        arrayOfInfo.append("Discounts: Opens a list of places that offer discounts to AUCians.")
        arrayOfInfo.append("Food Outlets: Opens a list of all food outlets on campus and their info.")
        arrayOfInfo.append("Facilities & Services: Opens a list of all facilities in the university, their services, and contact info.")
        arrayOfInfo.append("FYE Tab Features:")
        arrayOfInfo.append("3 Day Schedule: Contains a schedule for the 3 days of FYE.")
        arrayOfInfo.append("FYE Guide: The one stop guide to all the important facts needed for all freshmen.")
        arrayOfInfo.append("Competition: Shows the ranking for each house for the FYE competition.")
        arrayOfInfo.append("Youtube Channel: Lists all the videos available on FYE's Youtube channel to watch.")
        arrayOfInfo.append("Majors Fair: Opens a map for the majors fair.")
        arrayOfInfo.append("Engagement Fair: Opens a map for the engagement fair.")
        arrayOfInfo.append("FYE Stations: Opens a map for the FYE stations.")
        arrayOfInfo.append("Treasure Hunt: Opens a map for the treasure hunt.")
        
        arrayOfImages.append(#imageLiteral(resourceName: "helplogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "majorslogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "clubslogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "maplogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "messageboardslogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "faqlogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "linkslogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "discountslogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "foodlogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "facilitieslogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "helplogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "schedulelogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "guidelogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "competitionlogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "youtubelogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "majorfair"))
        arrayOfImages.append(#imageLiteral(resourceName: "engagementfair"))
        arrayOfImages.append(#imageLiteral(resourceName: "stationslogo"))
        arrayOfImages.append(#imageLiteral(resourceName: "treasurehuntlogo"))
        
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
     */
    
    override func viewWillAppear(_ animated: Bool) {
        UIApplication.shared.statusBarStyle = .lightContent
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
    }

}
