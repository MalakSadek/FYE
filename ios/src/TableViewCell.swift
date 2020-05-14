//
//  TableViewCell.swift
//  FYEApp
//
//  Created by Ibrahim Roshdy on 7/13/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import UIKit

class TableViewCell: UITableViewCell {
 
    @IBOutlet weak var myimage: UIImageView!
    @IBOutlet weak var mylabel: UILabel!
    

        override func awakeFromNib() {
        super.awakeFromNib()
     
        
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
